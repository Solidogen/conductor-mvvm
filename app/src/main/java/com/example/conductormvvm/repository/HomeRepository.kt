package com.example.conductormvvm.repository

import com.example.conductormvvm.data.domain.ShopData
import com.example.conductormvvm.data.domain.HomeData
import com.example.conductormvvm.data.domain.NewsData
import com.example.conductormvvm.data.mapper.toDomainModel
import com.example.conductormvvm.datasource.IHomeRemoteDataSource
import com.example.conductormvvm.util.utils.ApiCallResult
import com.example.conductormvvm.util.utils.IAppDispatchers
import com.example.conductormvvm.util.utils.runCatchingAsync

class HomeRepository(
    private val homeRemoteDataSource: IHomeRemoteDataSource,
    private val appDispatchers: IAppDispatchers
) {
    suspend fun getHomeData(): ApiCallResult<HomeData> = runCatchingAsync(
        call = { homeRemoteDataSource.getHomeData() },
        mapper = { it.toDomainModel() },
        appDispatchers = appDispatchers
    )

    suspend fun getShopData(): ApiCallResult<ShopData> = runCatchingAsync(
        call = { homeRemoteDataSource.getShopData() },
        mapper = { it.toDomainModel() },
        appDispatchers = appDispatchers
    )

    suspend fun getNewsData(): ApiCallResult<NewsData> = runCatchingAsync(
        call = { homeRemoteDataSource.getNewsData() },
        mapper = { it.toDomainModel() },
        appDispatchers = appDispatchers
    )
}
