package com.example.conductormvvm.ui.features.settings

import androidx.lifecycle.ViewModel
import com.example.conductormvvm.repository.SettingsRepository

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    var isUserRich: Boolean
        get() = settingsRepository.isUserRich
        set(value) { settingsRepository.isUserRich = value }
}