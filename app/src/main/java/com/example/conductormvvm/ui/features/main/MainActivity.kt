package com.example.conductormvvm.ui.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var mainController: MainController

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainController = MainController(this, savedInstanceState)
        setContentView(mainController.bindingRoot)
        mainController.setupViews()

        Timber.d(viewModel.toString())
        subscribeToViewModel()
    }

    override fun onBackPressed() {
        if (!mainController.onPhysicalBackButton()) {
            super.onBackPressed()
        }
    }

    private fun subscribeToViewModel() {

    }
}