package com.example.conductormvvm.ui.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.conductormvvm.util.extensions.observeEvents
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var globalUiManager: GlobalUiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        globalUiManager = GlobalUiManager(this, savedInstanceState)
        setContentView(globalUiManager.bindingRoot)
        globalUiManager.setupViews()
        subscribeToViewModel()
    }

    override fun onBackPressed() {
        if (!globalUiManager.onPhysicalBackButton()) {
            super.onBackPressed()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.fakeSocketMessages.observeEvents(this) {
            globalUiManager.fakeSocketMessageReceived(it)
        }
        viewModel.errors.observeEvents(this) { globalUiManager.errorReceived(it) }
        viewModel.navDestination.observeEvents(this) { globalUiManager.navigate(it) }
    }
}