package ph.skybridge.api.service

import io.smallrye.mutiny.Uni
import ph.skybridge.api.model.SuccessResp

interface BinService {
    fun verifyCard(cardNo: String): Uni<SuccessResp>
}