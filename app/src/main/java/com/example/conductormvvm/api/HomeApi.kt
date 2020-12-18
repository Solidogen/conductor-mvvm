package com.example.conductormvvm.api

import com.example.conductormvvm.data.response.HomeDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface IHomeApi {

    @GET("http://jsonplaceholder.typicode.com/todos/1")
    suspend fun getHomeData() : Response<HomeDataResponse>
}