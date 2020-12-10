package com.example.conductormvvm.ui.features.add

import com.example.conductormvvm.R
import com.example.conductormvvm.ui.base.BaseController
import org.koin.core.component.inject

class AddController : BaseController(R.layout.controller_add) {

    private val viewModel: AddViewModel by inject()

    override fun onViewCreated() {
        viewModel.toString()
        // todo subscribe to stateflow + sharedflow
    }
}


