package ir.iamsalar.ghaleh.network.model

import com.squareup.moshi.Json

data class TestModel(
    @Json(name = "id") val id: Int,
    @Json(name = "fullName") val fullName: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "profilePhoto") val profilePhoto: String
)
