package com.example.conductormvvm.ui.features.news

import com.example.conductormvvm.R
import com.example.conductormvvm.ui.base.BaseController
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject

class NewsController : BaseController(R.layout.controller_news) {

    private val viewModel: NewsViewModel by viewModel()

    override fun onViewCreated() {
        viewModel.toString()
        // todo subscribe to stateflow + sharedflow
    }
}
