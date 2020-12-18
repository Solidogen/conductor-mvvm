package com.example.conductormvvm.datasource

import com.example.conductormvvm.api.IHomeApi
import com.example.conductormvvm.data.response.ShopDataResponse
import com.example.conductormvvm.data.response.HomeDataResponse
import com.example.conductormvvm.data.response.NewsDataResponse
import com.example.conductormvvm.util.utils.bodyOrThrow

interface IHomeRemoteDataSource {
    suspend fun getHomeData(): HomeDataResponse
    suspend fun getShopData(): ShopDataResponse
    suspend fun getNewsData(): NewsDataResponse
}

class HomeRemoteDataSource(private val homeApi: IHomeApi) : IHomeRemoteDataSource {

    override suspend fun getHomeData() = homeApi.getHomeData().bodyOrThrow()
    override suspend fun getShopData() = homeApi.getShopData().bodyOrThrow()
    override suspend fun getNewsData() = homeApi.getNewsData().bodyOrThrow()
}