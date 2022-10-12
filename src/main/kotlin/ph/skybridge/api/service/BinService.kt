package ph.skybridge.api.service

import io.smallrye.mutiny.Uni
import ph.skybridge.api.model.SuccessResp

/**
 * Bin service
 *
 * This is the interface to declare service (eg. Bin API service, hence BinService)
 *
 * @constructor Create empty Bin service
 */
interface BinService {

    /**
     * Verify card
     *
     * Declares that the service must have a verifyCard feature
     *
     * @param cardNo
     * @return
     */
    fun verifyCard(cardNo: String): Uni<SuccessResp>
}