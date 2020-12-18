package com.example.conductormvvm.ui.features.home

import com.example.conductormvvm.R
import com.example.conductormvvm.databinding.ControllerHomeBinding
import com.example.conductormvvm.ui.base.BaseController
import com.example.conductormvvm.util.utils.NavDestination
import com.example.conductormvvm.util.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeController : BaseController(R.layout.controller_home) {

    private val binding: ControllerHomeBinding by viewBinding(ControllerHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated() {
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        binding.goToSettingsButton.setOnClickListener {
            viewModel.navigate(NavDestination.Settings)
        }
    }

    private fun observeViewModel() {
        viewModel.homeData.observe(this) {
            binding.contentTextView.text = it.toString()
        }
    }
}