package com.example.conductormvvm.util.utils.observable

import com.example.conductormvvm.util.utils.Event
import com.example.conductormvvm.util.utils.NavDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

interface ICanNavigate {
    fun navigate(navDestination: NavDestination)
}

class NavigationManager(private val globalScope: CoroutineScope) : ICanNavigate {

    private val _navDestination: MutableSharedFlow<Event<NavDestination>> = MutableSharedFlow()

    val navDestination: SharedFlow<Event<NavDestination>>
        get() = _navDestination

    override fun navigate(navDestination: NavDestination) {
        globalScope.launch {
            _navDestination.emit(Event(navDestination))
        }
    }
}