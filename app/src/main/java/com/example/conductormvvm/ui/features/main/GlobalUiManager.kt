package com.example.conductormvvm.ui.features.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.conductormvvm.R
import com.example.conductormvvm.data.domain.SocketMessage
import com.example.conductormvvm.databinding.ActivityMainBinding
import com.example.conductormvvm.ui.features.add.AddController
import com.example.conductormvvm.ui.features.home.HomeController
import com.example.conductormvvm.ui.features.news.NewsController
import com.example.conductormvvm.ui.features.settings.SettingsController
import com.example.conductormvvm.util.extensions.exhausting
import com.example.conductormvvm.util.utils.ErrorType
import com.example.conductormvvm.util.utils.NavDestination
import timber.log.Timber

/**
 * Do not inject this. Inject ErrorManager or NavigationManager to your ViewModel instead
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
                R.id.home_bottom_nav_item -> navigate(NavDestination.Home).let { true }
                R.id.news_bottom_nav_item -> navigate(NavDestination.News).let { true }
                R.id.add_bottom_nav_item -> navigate(NavDestination.Add).let { true }
                R.id.settings_bottom_nav_item -> navigate(NavDestination.Settings).let { true }
                else -> false
            }
        }
    }

    fun navigate(navDestination: NavDestination) {
        router.pushController(RouterTransaction.with(getControllerForNavDestination(navDestination)))
    }

    fun onPhysicalBackButton(): Boolean {
        return router.handleBack()
    }

    fun socketMessageReceived(socketMessage: SocketMessage) {
        Timber.d("Socket message received: $socketMessage")
    }

    fun errorReceived(errorType: ErrorType) {
        AlertDialog.Builder(mainActivity).setMessage(errorType.message).show()
    }

    // todo replace/setAsRoot + change handler type etc
    private fun getControllerForNavDestination(navDestination: NavDestination): Controller {
        return when (navDestination) {
            NavDestination.Home -> HomeController()
            NavDestination.Add -> AddController()
            NavDestination.News -> NewsController()
            NavDestination.Settings -> SettingsController()
        }.exhausting
    }
}