package com.example.conductormvvm.data.mapper

import com.example.conductormvvm.data.domain.HomeData
import com.example.conductormvvm.data.response.HomeDataResponse

fun HomeDataResponse.toDomainModel(): HomeData = HomeData(
    userId = userId,
    id = id,
    title = title,
    completed = completed
)