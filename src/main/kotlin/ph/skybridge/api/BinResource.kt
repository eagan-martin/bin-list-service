package ph.skybridge.api

import io.quarkus.vertx.http.Compressed
import io.quarkus.vertx.web.Param
import io.quarkus.vertx.web.Route
import io.smallrye.mutiny.Uni
import io.vertx.ext.web.RoutingContext
import org.eclipse.microprofile.opentracing.Traced
import ph.skybridge.api.model.SuccessResp
import ph.skybridge.api.service.BinService
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.core.MediaType

/**
 * Bin resource
 *
 * This is the class to expose API endpoints (eg. Bin API endpoints, hence BinResource)
 *
 * @property service
 * @constructor Create empty Bin resource
 */
@Traced
@ApplicationScoped
class BinResource(
    private val service: BinService
) {

    /**
     * Verify card
     *
     * This defines an API endpoint (eg. GET /v1/bin/verify)
     *
     * @param cardNo
     * @param rc
     * @return
     */
    @Route(
        methods = [Route.HttpMethod.GET],
        path = "/v1/bin/verify",
        produces = [MediaType.APPLICATION_JSON]
    )
    @Compressed
    fun verifyCard(
        @Param(value = "cardNo") cardNo: String,
        rc: RoutingContext
    ): Uni<SuccessResp> {
        return service.verifyCard(cardNo)
    }
}