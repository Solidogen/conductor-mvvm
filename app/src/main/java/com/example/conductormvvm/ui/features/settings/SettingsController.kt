package com.example.conductormvvm.ui.features.settings

import com.example.conductormvvm.R
import com.example.conductormvvm.databinding.ControllerSettingsBinding
import com.example.conductormvvm.ui.base.BaseController
import com.example.conductormvvm.util.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SettingsController : BaseController(R.layout.controller_settings) {

    private val binding: ControllerSettingsBinding by viewBinding(ControllerSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated() {
        Timber.d(viewModel.toString())
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.settingsData.observe(this) {
            binding.contentTextView.text = it.content
        }
    }
}