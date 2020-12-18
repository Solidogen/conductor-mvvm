package com.example.conductormvvm.util.utils.observable

import com.example.conductormvvm.util.utils.Event
import com.example.conductormvvm.util.utils.NavDestination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class NavigationManager {

    private val _navDestination: MutableSharedFlow<Event<NavDestination>> = MutableSharedFlow()

    val navDestination: SharedFlow<Event<NavDestination>>
        get() = _navDestination

    suspend fun navigate(navDestination: NavDestination) {
        _navDestination.emit(Event(navDestination))
    }
}