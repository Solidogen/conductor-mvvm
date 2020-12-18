package com.example.conductormvvm.ui.features.shop

import com.example.conductormvvm.R
import com.example.conductormvvm.databinding.ControllerShopBinding
import com.example.conductormvvm.ui.base.BaseController
import com.example.conductormvvm.util.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ShopController : BaseController(R.layout.controller_shop) {

    private val binding: ControllerShopBinding by viewBinding(ControllerShopBinding::bind)
    private val viewModel: ShopViewModel by viewModel()

    override fun onViewCreated() {
        Timber.d(viewModel.toString())
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.shopData.observe(this) {
            binding.contentTextView.text = it.toString()
        }
    }
}
