package com.example.conductormvvm.ui.features.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conductormvvm.data.domain.AddData
import com.example.conductormvvm.repository.AddRepository
import kotlinx.coroutines.launch

class AddViewModel(private val addRepository: AddRepository) : ViewModel() {

    private val _addData = MutableLiveData<AddData>()
    val addData: LiveData<AddData> get() = _addData

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val addData = addRepository.getAddData()
            _addData.value = addData
        }
    }
}