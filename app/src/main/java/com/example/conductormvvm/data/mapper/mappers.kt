package com.example.conductormvvm.data.mapper

import com.example.conductormvvm.data.domain.ShopData
import com.example.conductormvvm.data.domain.HomeData
import com.example.conductormvvm.data.domain.NewsData
import com.example.conductormvvm.data.domain.RealSocketMessage
import com.example.conductormvvm.data.response.ShopDataResponse
import com.example.conductormvvm.data.response.HomeDataResponse
import com.example.conductormvvm.data.response.NewsDataResponse
import com.example.conductormvvm.data.response.RealSocketMessageDto

fun HomeDataResponse.toDomainModel(): HomeData = HomeData(
    userId = userId,
    id = id,
    title = title,
    completed = completed
)

fun ShopDataResponse.toDomainModel(): ShopData = ShopData(
    userId = userId,
    id = id,
    title = title,
    completed = completed
)

fun NewsDataResponse.toDomainModel(): NewsData = NewsData(
    userId = userId,
    id = id,
    title = title,
    completed = completed
)

fun RealSocketMessageDto.toDomainModel(): RealSocketMessage = RealSocketMessage(
    username = username,
    isRich = isRich
)