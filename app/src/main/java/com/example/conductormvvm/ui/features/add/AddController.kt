package com.example.conductormvvm.ui.features.add

import com.example.conductormvvm.R
import com.example.conductormvvm.ui.base.BaseController
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddController : BaseController(R.layout.controller_add) {

    private val viewModel: AddViewModel by viewModel()

    override fun onViewCreated() {
        viewModel.toString()
        // todo subscribe to stateflow + sharedflow
    }
}


