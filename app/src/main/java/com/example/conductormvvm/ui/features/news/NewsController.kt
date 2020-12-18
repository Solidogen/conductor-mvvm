package com.example.conductormvvm.ui.features.news

import com.example.conductormvvm.R
import com.example.conductormvvm.databinding.ControllerHomeBinding
import com.example.conductormvvm.databinding.ControllerNewsBinding
import com.example.conductormvvm.ui.base.BaseController
import com.example.conductormvvm.util.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import timber.log.Timber

class NewsController : BaseController(R.layout.controller_news) {

    private val binding: ControllerNewsBinding by viewBinding(ControllerNewsBinding::bind)
    private val viewModel: NewsViewModel by viewModel()

    override fun onViewCreated() {
        Timber.d(viewModel.toString())
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.newsData.observe(this) {
            binding.contentTextView.text = it.toString()
        }
    }
}
