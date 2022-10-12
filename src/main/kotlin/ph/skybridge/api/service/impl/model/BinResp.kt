package ph.skybridge.api.service.impl.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class BinResp(
    @field:JsonProperty(value = "number")
    val number: BinNumber? = null,
    @field:JsonProperty(value = "scheme")
    val scheme: String? = null,
    @field:JsonProperty(value = "type")
    val type: String? = null,
    @field:JsonProperty(value = "brand")
    val brand: String? = null,
    @field:JsonProperty(value = "prepaid")
    val prepaid: Boolean? = null,
    @field:JsonProperty(value = "country")
    val country: BinCountry? = null,
    @field:JsonProperty(value = "bank")
    val bank: BinBank? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class BinNumber(
    @field:JsonProperty(value = "length")
    val length: Int? = null,
    @field:JsonProperty(value = "luhn")
    val luhn: Boolean? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class BinCountry(
    @field:JsonProperty(value = "numeric")
    val numeric: String? = null,
    @field:JsonProperty(value = "alpha2")
    val alpha2: String? = null,
    @field:JsonProperty(value = "name")
    val name: String? = null,
    @field:JsonProperty(value = "emoji")
    val emoji: String? = null,
    @field:JsonProperty(value = "currency")
    val currency: String? = null,
    @field:JsonProperty(value = "latitude")
    val latitude: Long? = null,
    @field:JsonProperty(value = "longitude")
    val longitude: Long? = null,
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class BinBank(
    @field:JsonProperty(value = "name")
    val name: String? = null,
    @field:JsonProperty(value = "url")
    val url: String? = null,
    @field:JsonProperty(value = "phone")
    val phone: String? = null,
    @field:JsonProperty(value = "city")
    val city: String? = null,
)





