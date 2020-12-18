package com.example.conductormvvm.api

import com.example.conductormvvm.data.response.AddDataResponse
import com.example.conductormvvm.data.response.HomeDataResponse
import com.example.conductormvvm.data.response.NewsDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface IHomeApi {

    @GET("http://jsonplaceholder.typicode.com/todos/1")
    suspend fun getHomeData() : Response<HomeDataResponse>

    @GET("http://jsonplaceholder.typicode.com/todos/2")
    suspend fun getAddData() : Response<AddDataResponse>

    @GET("http://jsonplaceholder.typicode.com/todos/3")
    suspend fun getNewsData() : Response<NewsDataResponse>
}