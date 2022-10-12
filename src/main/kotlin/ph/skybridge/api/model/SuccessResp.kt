package ph.skybridge.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Success resp
 *
 * This is the base response if there is no error
 *
 * @property payload - response payload
 * @property signature - payload signature
 * @constructor Create empty Success resp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class SuccessResp(
    @field:JsonProperty(value = "payload")
    val payload: Any? = null,
    @field:JsonProperty(value = "signature")
    val signature: String? = null,
)





