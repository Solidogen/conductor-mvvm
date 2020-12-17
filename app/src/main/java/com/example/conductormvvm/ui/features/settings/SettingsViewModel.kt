package com.example.conductormvvm.ui.features.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conductormvvm.data.domain.SettingsData
import com.example.conductormvvm.repository.SettingsRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    private val _settingsData = MutableLiveData<SettingsData>()
    val settingsData: LiveData<SettingsData> get() = _settingsData

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val settingsData = settingsRepository.getSettingsData()
            _settingsData.value = settingsData
        }
    }
}