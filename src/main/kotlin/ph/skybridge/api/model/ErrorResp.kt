package ph.skybridge.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Error resp
 *
 * This is the base response whenever an error has occured
 *
 * @property traceId - correlation id
 * @property detail - detail of the error
 * @property violations - list of fields that are violated
 * @property payload - response payload
 * @property signature - payload signature
 * @constructor Create empty Error resp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ErrorResp(
    @field:JsonProperty(value = "traceId")
    val traceId: String? = null,
    @field:JsonProperty(value = "detail")
    val detail: String? = null,
    @field:JsonProperty(value = "violations")
    val violations: ArrayList<FieldViolation>? = null,
    @field:JsonProperty(value = "payload")
    val payload: Any? = null,
    @field:JsonProperty(value = "signature")
    val signature: String? = null,
)

/**
 * Field violation
 *
 * This is the model for violated fields
 *
 * @property field - violated field name
 * @property message - field requirement/s that needs to be satisfied
 * @constructor Create empty Field violation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class FieldViolation(
    @field:JsonProperty(value = "field")
    val field: String? = null,
    @field:JsonProperty(value = "message")
    val message: String? = null,
)