package ph.skybridge.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class FieldViolation(
    @field:JsonProperty(value = "field")
    val field: String? = null,
    @field:JsonProperty(value = "message")
    val message: String? = null,
)