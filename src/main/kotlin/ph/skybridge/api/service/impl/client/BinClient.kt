package ph.skybridge.api.service.impl.client

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.opentracing.Traced
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import ph.skybridge.api.service.impl.model.BinResp
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Traced
@RegisterRestClient(configKey = "bin-service")
interface BinClient {
    @GET
    @Path("/{cardNo}")
    @Produces(value = [MediaType.APPLICATION_JSON])
    fun verifyCard(
        @PathParam(value = "cardNo") cardNo: String
    ): Uni<BinResp>
}