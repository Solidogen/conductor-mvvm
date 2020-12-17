package com.example.conductormvvm.di

import com.example.conductormvvm.BuildConfig
import com.example.conductormvvm.api.IHomeApi
import com.example.conductormvvm.datasource.HomeRemoteDataSource
import com.example.conductormvvm.datasource.IHomeRemoteDataSource
import com.example.conductormvvm.repository.*
import com.example.conductormvvm.ui.features.add.AddViewModel
import com.example.conductormvvm.ui.features.home.HomeViewModel
import com.example.conductormvvm.ui.features.main.MainViewModel
import com.example.conductormvvm.ui.features.news.NewsViewModel
import com.example.conductormvvm.ui.features.settings.SettingsViewModel
import com.example.conductormvvm.util.utils.AppDispatchers
import com.example.conductormvvm.util.utils.ErrorManager
import com.example.conductormvvm.util.utils.IAppDispatchers
import com.example.conductormvvm.util.utils.Injection
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.MainScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

// todo break this into modules
val appModule = module {
    single(named(Injection.GlobalScope)) { MainScope() }
    single<IAppDispatchers> { AppDispatchers() }
    single { ErrorManager() }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        }
    }

    single<Moshi> {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    single<Converter.Factory> {
        MoshiConverterFactory.create(get())
    }

    single<Retrofit.Builder> {
        Retrofit.Builder()
            .baseUrl("https://test.com")
            .addConverterFactory(get())
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single<Retrofit> {
        get<Retrofit.Builder>()
            .client(get())
            .build()
    }

    single<IHomeApi> {
        get<Retrofit>().create(IHomeApi::class.java)
    }

    single<IHomeRemoteDataSource> {
        HomeRemoteDataSource(homeApi = get())
    }

    single { GlobalEventRepository(globalScope = get(named(Injection.GlobalScope))) }
    single { HomeRepository(homeRemoteDataSource = get(), appDispatchers = get()) }
    single { NewsRepository() }
    single { AddRepository() }
    single { SettingsRepository() }

    viewModel { MainViewModel(globalEventRepository = get(), errorManager = get()) }
    viewModel { HomeViewModel(homeRepository = get(), errorManager = get()) }
    viewModel { NewsViewModel(newsRepository = get()) }
    viewModel { AddViewModel(addRepository = get()) }
    viewModel { SettingsViewModel(settingsRepository = get()) }
}