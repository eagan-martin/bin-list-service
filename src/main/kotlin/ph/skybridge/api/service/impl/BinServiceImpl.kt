package ph.skybridge.api.service.impl

import io.opentracing.Tracer
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.opentracing.Traced
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.jboss.logging.Logger
import ph.skybridge.api.model.SuccessResp
import ph.skybridge.api.service.BinService
import ph.skybridge.api.service.impl.client.BinClient
import ph.skybridge.api.service.impl.model.BinResp
import ph.skybridge.api.util.TraceUtil
import java.time.Duration
import javax.enterprise.context.ApplicationScoped

/**
 * Bin service impl
 *
 * This is the class to implement the service and its features (eg. Bin service, hence BinServiceImpl)
 *
 * @property tracer
 * @property log
 * @property client
 * @constructor Create empty Bin impl
 */
@Traced
@ApplicationScoped
class BinServiceImpl(
    private val tracer: Tracer?,
    private val log: Logger,
    @RestClient private val client: BinClient
): BinService { // this indicates that this class is an implementation of a service (eg. BinService)

    /**
     * Verify card
     *
     * This defines the logic for the verifyCard service feature
     *
     * @param cardNo
     * @return
     */
    override fun verifyCard(cardNo: String): Uni<SuccessResp> {
        val traceId: String = TraceUtil.provideTraceId(tracer)

        this.log.info("$traceId - attempting to verify card no, $cardNo")

        lateinit var successResp: SuccessResp

        return this.client.verifyCard(cardNo)
            .onFailure().retry()
                .withBackOff(Duration.ofSeconds(1))
                .withJitter(0.2)
                .atMost(2)
            .onFailure().invoke { exception ->
                val stackTrace: String = exception.stackTraceToString()
                    .replace("\\n".toRegex(), "")
                    .replace("[\n\r]".toRegex(), "")
                    .replace("    ", "")
                    .replace("  ", "")

                val data: Map<String, Any?> = mapOf(
                    "cause" to exception.cause,
                    "message" to exception.message,
                    "localizedMessage" to exception.localizedMessage,
                    "suppressed" to exception.suppressed,
                    "suppressedExceptions" to exception.suppressedExceptions,
                    /**
                     * Enables stacktrace to be added on the logs
                     */
//                    "stackTrace" to stackTrace,
                )

                this.log.warn("$traceId - an exception has occurred, $data")
            }
            /**
             * Enables transformation of an expcetion to another
             */
//            .onFailure().transform { _ ->
//                throw ServiceUnavailableException()
//            }
            /**
             * Enables recovery from an exception using an item
             */
            .onFailure().recoverWithItem { _ ->
                val recoveryItem = BinResp()

                this.log.warn("$traceId - recovering from exception with item, $recoveryItem")

                recoveryItem
            }
            .onItem().invoke { resp ->
                successResp = SuccessResp(payload = resp)
            }
            .onItem().invoke { _ ->
                this.log.info("$traceId - attempt to verify card no, $cardNo, was successful")
            }
            .onItem().transform { successResp }
    }
}