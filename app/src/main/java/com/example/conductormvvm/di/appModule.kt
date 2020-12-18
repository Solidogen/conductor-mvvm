package com.example.conductormvvm.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.conductormvvm.BuildConfig
import com.example.conductormvvm.api.IHomeApi
import com.example.conductormvvm.datasource.HomeRemoteDataSource
import com.example.conductormvvm.datasource.IHomeRemoteDataSource
import com.example.conductormvvm.datasource.ISettingsLocalDataSource
import com.example.conductormvvm.datasource.SettingsLocalDataSource
import com.example.conductormvvm.repository.*
import com.example.conductormvvm.ui.features.shop.ShopViewModel
import com.example.conductormvvm.ui.features.home.HomeViewModel
import com.example.conductormvvm.ui.features.main.MainViewModel
import com.example.conductormvvm.ui.features.news.NewsViewModel
import com.example.conductormvvm.ui.features.settings.SettingsViewModel
import com.example.conductormvvm.util.utils.AppDispatchers
import com.example.conductormvvm.util.utils.observable.ErrorManager
import com.example.conductormvvm.util.utils.IAppDispatchers
import com.example.conductormvvm.util.utils.Injection
import com.example.conductormvvm.util.utils.observable.NavigationManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.MainScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import org.koin.android.ext.koin.androidContext
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
    single { NavigationManager() }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        }
    }

    single<Moshi> { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }
    single<Converter.Factory> { MoshiConverterFactory.create(get()) }

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

    single {
        val fileName = "2vn345892v0n45"
        EncryptedSharedPreferences.create(
            fileName,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            androidContext(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single<IHomeApi> { get<Retrofit>().create(IHomeApi::class.java) }
    single<IHomeRemoteDataSource> { HomeRemoteDataSource(homeApi = get()) }

    single<ISettingsLocalDataSource> { SettingsLocalDataSource(sharedPreferences = get()) }

    single { WebSocketRepository(globalScope = get(named(Injection.GlobalScope))) }
    single { HomeRepository(homeRemoteDataSource = get(), appDispatchers = get()) }
    single { SettingsRepository(settingsLocalDataSource = get()) }

    viewModel {
        MainViewModel(
            webSocketRepository = get(),
            errorManager = get(),
            navigationManager = get()
        )
    }
    viewModel {
        HomeViewModel(
            homeRepository = get(),
            errorManager = get(),
            navigationManager = get()
        )
    }
    viewModel { NewsViewModel(homeRepository = get(), errorManager = get()) }
    viewModel { ShopViewModel(homeRepository = get(), errorManager = get()) }
    viewModel { SettingsViewModel(settingsRepository = get()) }
}