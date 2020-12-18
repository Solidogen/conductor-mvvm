package com.example.conductormvvm.util.utils

sealed class NavDestination {
    object Home : NavDestination()
    object Add : NavDestination()
    object News : NavDestination()
    object Settings : NavDestination()
}
