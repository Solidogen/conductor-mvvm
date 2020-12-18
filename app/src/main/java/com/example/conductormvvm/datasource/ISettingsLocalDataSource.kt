package com.example.conductormvvm.datasource

import android.content.SharedPreferences
import com.example.conductormvvm.util.extensions.commitBoolean

interface ISettingsLocalDataSource {
    var isUserRich: Boolean
}

class SettingsLocalDataSource(private val sharedPreferences: SharedPreferences) :
    ISettingsLocalDataSource {

    override var isUserRich: Boolean
        get() = sharedPreferences.getBoolean(IS_USER_RICH_KEY, false)
        set(value) = sharedPreferences.commitBoolean(IS_USER_RICH_KEY, value)

    companion object {
        private const val IS_USER_RICH_KEY = "1jhvlsdhglvsdg"
    }
}