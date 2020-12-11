package com.example.conductormvvm.di

import com.example.conductormvvm.repository.*
import com.example.conductormvvm.ui.features.add.AddViewModel
import com.example.conductormvvm.ui.features.home.HomeViewModel
import com.example.conductormvvm.ui.features.main.MainViewModel
import com.example.conductormvvm.ui.features.news.NewsViewModel
import com.example.conductormvvm.ui.features.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { GlobalEventRepository() }
    single { HomeRepository() }
    single { NewsRepository() }
    single { AddRepository() }
    single { SettingsRepository() }
    viewModel { MainViewModel(globalEventRepository = get()) }
    viewModel { HomeViewModel(homeRepository = get()) }
    viewModel { NewsViewModel(newsRepository = get()) }
    viewModel { AddViewModel(addRepository = get()) }
    viewModel { SettingsViewModel(settingsRepository = get()) }
}