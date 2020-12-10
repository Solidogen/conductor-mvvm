package com.example.conductormvvm.ui.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainController: MainController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainController = MainController(this, savedInstanceState)
        setContentView(mainController.bindingRoot)
        mainController.setupViews()
    }
}