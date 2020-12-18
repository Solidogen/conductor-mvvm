package com.example.conductormvvm.util.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface IAppDispatchers {
    /**
     * Main is not used in code, since we can swap main by Dispatchers.setMain in tests.
     * Not sure yet if I should remove this and rename to IBackgroundDispatchers
     * */
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}

class AppDispatchers : IAppDispatchers {

    override val main
        get() = Dispatchers.Main

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}