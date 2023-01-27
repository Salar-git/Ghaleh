package ir.iamsalar.ghaleh.network.model

import com.squareup.moshi.Json

data class LikeResponseModel(
    @Json(name = "message") val message: String
)
