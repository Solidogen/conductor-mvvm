package com.example.conductormvvm.ui.features.main

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.conductormvvm.R
import com.example.conductormvvm.data.GlobalEvent
import com.example.conductormvvm.databinding.ActivityMainBinding
import com.example.conductormvvm.ui.features.add.AddController
import com.example.conductormvvm.ui.features.home.HomeController
import com.example.conductormvvm.ui.features.news.NewsController
import com.example.conductormvvm.ui.features.settings.SettingsController
import timber.log.Timber

/**
 * This cannot be injected into controller, since activity will be leaked. I can test leaking BTW.
 *
 * Also - this may get big, may be needed to split it info a few classes
 * */
class GlobalUiManager(private val mainActivity: MainActivity, savedInstanceState: Bundle?) {

    private val binding = ActivityMainBinding.inflate(mainActivity.layoutInflater)
    private val router: Router
    val bindingRoot: ViewGroup

    init {
        router =
            Conductor.attachRouter(mainActivity, binding.controllerContainer, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(HomeController()))
        }
        bindingRoot = binding.root
    }

    fun setupViews() {
        binding.bottomNavView.setOnNavigationItemSelectedListener block@{ item ->
            return@block when (item.itemId) {
                R.id.home_bottom_nav_item -> navigate(HomeController()).let { true }
                R.id.news_bottom_nav_item -> navigate(NewsController()).let { true }
                R.id.add_bottom_nav_item -> navigate(AddController()).let { true }
                R.id.settings_bottom_nav_item -> navigate(SettingsController()).let { true }
                else -> false
            }
        }
    }

    fun navigate(controller: Controller) {
        router.pushController(RouterTransaction.with(controller))
    }

    fun onPhysicalBackButton(): Boolean {
        return router.handleBack()
    }

    fun globalEventReceived(globalEvent: GlobalEvent) {
        Timber.d("Global event received: $globalEvent")
    }
}