package com.example.conductormvvm.ui.features.home

import com.example.conductormvvm.R
import com.example.conductormvvm.ui.base.BaseController
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeController : BaseController(R.layout.controller_home) {

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated() {
        Timber.d(viewModel.toString())
        // todo subscribe to stateflow + sharedflow
    }
}