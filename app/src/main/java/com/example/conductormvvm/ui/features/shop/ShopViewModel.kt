package com.example.conductormvvm.ui.features.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conductormvvm.data.domain.ShopData
import com.example.conductormvvm.repository.HomeRepository
import com.example.conductormvvm.util.utils.observable.ErrorManager
import kotlinx.coroutines.launch

class ShopViewModel(
    private val homeRepository: HomeRepository,
    private val errorManager: ErrorManager
) : ViewModel() {

    private val _shopData = MutableLiveData<ShopData>()
    val shopData: LiveData<ShopData> get() = _shopData

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            homeRepository.getShopData()
                .onSuccess { _shopData.value = it }
                .onError { errorManager.handleApiError(it) }
        }
    }
}