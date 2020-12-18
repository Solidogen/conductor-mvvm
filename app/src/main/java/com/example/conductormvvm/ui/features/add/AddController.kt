package com.example.conductormvvm.ui.features.add

import com.example.conductormvvm.R
import com.example.conductormvvm.databinding.ControllerAddBinding
import com.example.conductormvvm.ui.base.BaseController
import com.example.conductormvvm.util.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class AddController : BaseController(R.layout.controller_add) {

    private val binding: ControllerAddBinding by viewBinding(ControllerAddBinding::bind)
    private val viewModel: AddViewModel by viewModel()

    override fun onViewCreated() {
        Timber.d(viewModel.toString())
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.addData.observe(this) {
            binding.contentTextView.text = it.toString()
        }
    }
}
