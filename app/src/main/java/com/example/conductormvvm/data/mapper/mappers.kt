package com.example.conductormvvm.data.mapper

import com.example.conductormvvm.data.domain.AddData
import com.example.conductormvvm.data.domain.HomeData
import com.example.conductormvvm.data.domain.NewsData
import com.example.conductormvvm.data.response.AddDataResponse
import com.example.conductormvvm.data.response.HomeDataResponse
import com.example.conductormvvm.data.response.NewsDataResponse

fun HomeDataResponse.toDomainModel(): HomeData = HomeData(
    userId = userId,
    id = id,
    title = title,
    completed = completed
)

fun AddDataResponse.toDomainModel(): AddData = AddData(
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