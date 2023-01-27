package ir.iamsalar.ghaleh.network.model

import com.squareup.moshi.Json

data class ResponseModel(
    @Json(name = "status") val status: Boolean,
    @Json(name = "message") val message: String,
    @Json(name = "data") val data: Any?
)
