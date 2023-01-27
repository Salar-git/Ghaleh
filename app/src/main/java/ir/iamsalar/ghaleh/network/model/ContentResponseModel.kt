package ir.iamsalar.ghaleh.network.model

import com.squareup.moshi.Json

data class ContentResponseModel(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "author") val author: String,
    @Json(name = "category") val category: String,
    @Json(name = "likes") val likes: Int,
)
