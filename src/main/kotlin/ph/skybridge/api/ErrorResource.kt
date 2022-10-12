package ph.skybridge.api

import io.netty.handler.codec.http.HttpResponseStatus
import io.netty.handler.timeout.TimeoutException
import io.opentracing.Tracer
import io.quarkus.vertx.web.Route
import io.smallrye.mutiny.Uni
import io.vertx.core.http.HttpServerResponse
import org.eclipse.microprofile.opentracing.Traced
import org.jboss.logging.Logger
import org.jboss.resteasy.reactive.ClientWebApplicationException
import ph.skybridge.api.model.ErrorResp
import ph.skybridge.api.model.FieldViolation
import ph.skybridge.api.util.TraceUtil
import javax.enterprise.context.ApplicationScoped
import javax.validation.ConstraintViolationException
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 * Error resource
 *
 * This is the class for error handling
 *
 * @property tracer
 * @property log
 * @constructor Create empty Error resource
 */
@Traced
@ApplicationScoped
class ErrorResource(
    private val tracer: Tracer?,
    private val log: Logger,
) {

    /**
     * On exception
     *
     * This defines an action/logic to handle each identified exceptions
     *
     * @param exception
     * @param resp
     * @return
     */
    @Route(
        type = Route.HandlerType.FAILURE,
        produces = [MediaType.APPLICATION_JSON]
    )
    fun onException(
        exception: Exception,
        resp: HttpServerResponse
    ): Uni<ErrorResp> {
        val traceId: String = TraceUtil.provideTraceId(tracer)

        this.log.info("$traceId - router error handling triggered by exception $exception")

        val data = mapOf(
            "cause" to exception.cause,
            "message" to exception.message,
            "localizedMessage" to exception.localizedMessage,
            "suppressed" to exception.suppressed,
            "suppressedExceptions" to exception.suppressedExceptions,
            "stackTrace" to exception.stackTraceToString(),
        )

        this.log.warn("$traceId - auditing exception, $data")

        val error: ErrorResp = when (exception) {
            is ConstraintViolationException -> {
                val violations: ArrayList<FieldViolation> = arrayListOf()

                exception.constraintViolations.iterator().forEach { violation ->
                    violations.add(
                        FieldViolation(
                            field = violation.propertyPath.toString().split(".").last(),
                            message = violation.message
                        )
                    )
                }

                resp.statusCode = HttpResponseStatus.BAD_REQUEST.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "invalid request",
                    violations = violations
                )
            }
            is NullPointerException  -> {
                val violations: ArrayList<FieldViolation> = arrayListOf()

                violations.add(
                    FieldViolation(
                        field = exception.message?.split(" ")?.last(),
                        message = "null value not allowed"
                    )
                )

                resp.statusCode = HttpResponseStatus.BAD_REQUEST.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "invalid request",
                    violations = violations
                )
            }
            is BadRequestException -> {
                resp.statusCode = HttpResponseStatus.BAD_REQUEST.code()

                ErrorResp(
                    traceId = traceId,
                    detail = exception.message ?: "invalid request",
                )
            }
            is IllegalArgumentException -> {
                var statusCode: Int = HttpResponseStatus.BAD_REQUEST.code()
                var message: String = "invalid request"

                if (exception.message?.contains("base64") == true) {
                    statusCode = HttpResponseStatus.UNAUTHORIZED.code()
                    message = "unauthorized"
                }

                resp.statusCode = statusCode

                ErrorResp(
                    traceId = traceId,
                    detail = message
                )
            }
            is NotAcceptableException -> {
                resp.statusCode = HttpResponseStatus.NOT_ACCEPTABLE.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "not acceptable",
                )
            }
            is NotAllowedException -> {
                resp.statusCode = HttpResponseStatus.METHOD_NOT_ALLOWED.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "method not allowed",
                )
            }
            is io.quarkus.security.ForbiddenException,
            is ForbiddenException -> {
                resp.statusCode = HttpResponseStatus.FORBIDDEN.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "forbidden",
                )
            }
            is NotAuthorizedException -> {
                resp.statusCode = HttpResponseStatus.UNAUTHORIZED.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "unauthorized",
                )
            }
            is NotFoundException -> {
                resp.statusCode = HttpResponseStatus.NOT_FOUND.code()

                ErrorResp(
                    traceId = traceId,
                    detail = exception.message ?: "not found",
                )
            }
            is NotSupportedException -> {
                resp.statusCode = HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "media type not supported",
                )
            }
            is ProcessingException -> {
                resp.statusCode = HttpResponseStatus.UNPROCESSABLE_ENTITY.code()

                ErrorResp(
                    traceId = traceId,
                    detail = exception.message ?: "unprocessable",
                )
            }
            is RedirectionException -> {
                resp.statusCode = HttpResponseStatus.MISDIRECTED_REQUEST.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "misdirected request",
                )
            }
            is ServiceUnavailableException  -> {
                resp.statusCode = HttpResponseStatus.SERVICE_UNAVAILABLE.code()

                ErrorResp(
                    traceId = traceId,
                    detail = exception.message ?: "service temporarily unavailable",
                )
            }
            is ServerErrorException -> {
                resp.statusCode = HttpResponseStatus.INTERNAL_SERVER_ERROR.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "internal server error",
                )
            }
            is WebApplicationException -> {
                val status: HttpResponseStatus = HttpResponseStatus.valueOf(exception.response.status)

                resp.statusCode = status.code()

                ErrorResp(
                    traceId = traceId,
                    detail = status.reasonPhrase() ?: "unexpected web application error",
                )
            }
            is TimeoutException -> {
                resp.statusCode = HttpResponseStatus.REQUEST_TIMEOUT.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "request timeout",
                )
            }
            is ClientWebApplicationException -> {
                val status: HttpResponseStatus = HttpResponseStatus.valueOf(exception.response.status)

                resp.statusCode = status.code()

                ErrorResp(
                    traceId = traceId,
                    detail = status.reasonPhrase() ?: "unexpected client web application error",
                )
            }
            /**
             * This is a fail-safe handling for all unidentified exceptions
             */
            else -> {
                this.log.warn("$traceId - gracefully handling unidentified/generic exception")

                resp.statusCode = HttpResponseStatus.INTERNAL_SERVER_ERROR.code()

                ErrorResp(
                    traceId = traceId,
                    detail = "internal server error",
                )
            }
        }

        return Uni.createFrom().item(error)
    }
}