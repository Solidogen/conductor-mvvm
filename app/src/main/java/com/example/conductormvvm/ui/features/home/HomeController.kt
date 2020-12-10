package com.example.conductormvvm.ui.features.home

import com.example.conductormvvm.R
import com.example.conductormvvm.ui.base.BaseController
import org.koin.core.component.inject
import timber.log.Timber

class HomeController : BaseController(R.layout.controller_home) {

    private val viewModel: HomeViewModel by inject()

    override fun onViewCreated() {
        Timber.d(viewModel.toString())
        // todo subscribe to stateflow + sharedflow
    }
}