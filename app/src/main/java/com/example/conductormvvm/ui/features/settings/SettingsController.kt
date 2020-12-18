package com.example.conductormvvm.ui.features.settings

import com.example.conductormvvm.R
import com.example.conductormvvm.databinding.ControllerSettingsBinding
import com.example.conductormvvm.ui.base.BaseController
import com.example.conductormvvm.util.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsController : BaseController(R.layout.controller_settings) {

    private val binding: ControllerSettingsBinding by viewBinding(ControllerSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated() {
        setupViews()
    }

    private fun setupViews() {
        binding.isUserRichCheckbox.apply {
            isChecked = viewModel.isUserRich
            setOnCheckedChangeListener { _, isChecked -> viewModel.isUserRich = isChecked }
        }
    }
}