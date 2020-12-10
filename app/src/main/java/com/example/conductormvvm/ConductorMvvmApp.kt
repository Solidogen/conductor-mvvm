package com.example.conductormvvm

import android.app.Application
import com.example.conductormvvm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ConductorMvvmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ConductorMvvmApp)
            modules(
                listOf(
                    appModule
                )
            )
        }
    }
}