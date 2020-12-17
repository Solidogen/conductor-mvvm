package com.example.conductormvvm.repository

import com.example.conductormvvm.data.domain.HomeData
import com.example.conductormvvm.data.response.HomeDataResponse
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
}

fun HomeDataResponse.toDomainModel(): HomeData = HomeData(
    content = content
)