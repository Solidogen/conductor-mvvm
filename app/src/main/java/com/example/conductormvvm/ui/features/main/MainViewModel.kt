package com.example.conductormvvm.ui.features.main

import androidx.lifecycle.*
import com.example.conductormvvm.data.domain.FakeSocketMessage
import com.example.conductormvvm.data.domain.RealSocketMessage
import com.example.conductormvvm.repository.WebSocketRepository
import com.example.conductormvvm.util.utils.observable.ErrorManager
import com.example.conductormvvm.util.utils.ErrorType
import com.example.conductormvvm.util.utils.Event
import com.example.conductormvvm.util.utils.NavDestination
import com.example.conductormvvm.util.utils.observable.NavigationManager

class MainViewModel(
    private val webSocketRepository: WebSocketRepository,
    private val errorManager: ErrorManager,
    private val navigationManager: NavigationManager
) : ViewModel() {

    val realSocketMessages: LiveData<RealSocketMessage>
        get() = webSocketRepository.realSocketMessages.asLiveData()

    val fakeSocketMessages: LiveData<FakeSocketMessage>
        get() = webSocketRepository.fakeSocketMessages.asLiveData()

    val errors: LiveData<Event<ErrorType>>
        get() = errorManager.errors.asLiveData()

    val navDestination: LiveData<Event<NavDestination>>
        get() = navigationManager.navDestination.asLiveData()
}