package com.example.conductormvvm.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.conductormvvm.ui.features.add.AddViewModel
import com.example.conductormvvm.ui.features.home.HomeViewModel
import com.example.conductormvvm.ui.features.main.MainViewModel
import com.example.conductormvvm.ui.features.news.NewsViewModel
import com.example.conductormvvm.ui.features.settings.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // activity viewmodel
    viewModel { MainViewModel() }

    // controller viewmodels
    single { ViewModelStore() }
    single<ViewModelProvider.Factory> {
        ViewModelProvider.AndroidViewModelFactory(androidApplication())
    }
    single {
        ControllerViewModelProvider(
            viewModelStore = get(),
            factory = get()
        )
    }

    // those do not use ViewModelFactory
    // but I don't think I should... they survive regardless, because controllers survive as well.
    factory { get<ControllerViewModelProvider>().get(HomeViewModel::class.java) }
    factory { NewsViewModel() }
    factory { AddViewModel() }
    factory { SettingsViewModel() }
}

class ControllerViewModelProvider(viewModelStore: ViewModelStore, factory: Factory) :
    ViewModelProvider(viewModelStore, factory)