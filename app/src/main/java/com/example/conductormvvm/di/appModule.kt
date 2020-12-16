package com.example.conductormvvm.di

import com.example.conductormvvm.repository.*
import com.example.conductormvvm.ui.features.add.AddViewModel
import com.example.conductormvvm.ui.features.home.HomeViewModel
import com.example.conductormvvm.ui.features.main.MainViewModel
import com.example.conductormvvm.ui.features.news.NewsViewModel
import com.example.conductormvvm.ui.features.settings.SettingsViewModel
import com.example.conductormvvm.util.utils.ApiCallWrapper
import com.example.conductormvvm.util.utils.AppDispatchers
import com.example.conductormvvm.util.utils.IAppDispatchers
import com.example.conductormvvm.util.utils.Injection
import kotlinx.coroutines.MainScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named(Injection.GlobalScope)) { MainScope() }
    single<IAppDispatchers> { AppDispatchers() }
    single { ApiCallWrapper() }
    single { GlobalEventRepository(globalScope = get(named(Injection.GlobalScope))) }
    single { HomeRepository(apiCallWrapper = get()) }
    single { NewsRepository() }
    single { AddRepository() }
    single { SettingsRepository() }
    viewModel { MainViewModel(globalEventRepository = get()) }
    viewModel { HomeViewModel(homeRepository = get()) }
    viewModel { NewsViewModel(newsRepository = get()) }
    viewModel { AddViewModel(addRepository = get()) }
    viewModel { SettingsViewModel(settingsRepository = get()) }
}