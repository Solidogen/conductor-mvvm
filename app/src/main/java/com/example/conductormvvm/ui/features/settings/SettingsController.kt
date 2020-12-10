package com.example.conductormvvm.ui.features.settings

import com.example.conductormvvm.R
import com.example.conductormvvm.ui.base.BaseController
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsController : BaseController(R.layout.controller_settings) {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated() {
        viewModel.toString()
        // todo subscribe to stateflow + sharedflow
    }
}