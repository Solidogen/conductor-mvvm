package com.example.conductormvvm.ui.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainController: MainController

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainController = MainController(this, savedInstanceState)
        setContentView(mainController.bindingRoot)
        mainController.setupViews()

        viewModel.toString()
        // todo subscribe to stateflow + sharedflow
    }
}