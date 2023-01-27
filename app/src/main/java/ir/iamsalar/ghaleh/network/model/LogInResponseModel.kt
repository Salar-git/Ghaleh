package ir.iamsalar.ghaleh.network.model

import com.squareup.moshi.Json

data class LogInResponseModel(
    @Json(name = "token") val token: String
)
