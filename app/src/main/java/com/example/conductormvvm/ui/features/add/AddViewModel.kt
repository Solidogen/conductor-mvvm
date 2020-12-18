package com.example.conductormvvm.ui.features.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conductormvvm.data.domain.AddData
import com.example.conductormvvm.repository.HomeRepository
import com.example.conductormvvm.util.utils.observable.ErrorManager
import kotlinx.coroutines.launch

class AddViewModel(
    private val homeRepository: HomeRepository,
    private val errorManager: ErrorManager
) : ViewModel() {

    private val _addData = MutableLiveData<AddData>()
    val addData: LiveData<AddData> get() = _addData

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            homeRepository.getAddData()
                .onSuccess { _addData.value = it }
                .onError { errorManager.handleApiError(it) }
        }
    }
}