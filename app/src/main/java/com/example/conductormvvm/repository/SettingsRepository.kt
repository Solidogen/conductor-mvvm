package com.example.conductormvvm.repository

import com.example.conductormvvm.datasource.ISettingsLocalDataSource

class SettingsRepository(private val settingsLocalDataSource: ISettingsLocalDataSource) {

    var isUserRich: Boolean
        get() = settingsLocalDataSource.isUserRich
        set(value) { settingsLocalDataSource.isUserRich = value }
}