package com.example.conductormvvm.datasource

import com.example.conductormvvm.api.IHomeApi
import com.example.conductormvvm.data.response.HomeDataResponse
import com.example.conductormvvm.util.utils.bodyOrThrow

interface IHomeRemoteDataSource {
    suspend fun getHomeData(): HomeDataResponse
}

class HomeRemoteDataSource(private val homeApi: IHomeApi) : IHomeRemoteDataSource {

    override suspend fun getHomeData() = homeApi.getHomeData().bodyOrThrow()
}