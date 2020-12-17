package com.example.conductormvvm.api

import com.example.conductormvvm.data.response.HomeDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface IHomeApi {

    @GET("/")
    suspend fun getHomeData() : Response<HomeDataResponse>
}