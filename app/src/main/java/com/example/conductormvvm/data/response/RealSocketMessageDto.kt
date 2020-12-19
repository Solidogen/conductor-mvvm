package com.example.conductormvvm.data.response

import com.squareup.moshi.Json

data class RealSocketMessageDto(
    @Json(name = "username")
    val username: String,
    @Json(name = "isRich")
    val isRich: Boolean
)