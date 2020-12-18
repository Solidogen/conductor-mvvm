package com.example.conductormvvm.data.response

import com.squareup.moshi.Json

data class NewsDataResponse(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "completed")
    val completed: Boolean
)