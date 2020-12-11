package com.example.conductormvvm.util.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface IAppDispatchers {
    val mainDispatcher: CoroutineDispatcher
    val ioDispatcher: CoroutineDispatcher
    val defaultDispatcher: CoroutineDispatcher
}

class AppDispatchers : IAppDispatchers {

    override val mainDispatcher
        get() = Dispatchers.Main

    override val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    override val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default
}