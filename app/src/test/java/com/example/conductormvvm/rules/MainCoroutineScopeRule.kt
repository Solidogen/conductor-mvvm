package com.example.conductormvvm.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainCoroutineScopeRule(
    dispatcherInitializer: () -> TestCoroutineDispatcher,
    scopeInitializer: () -> TestCoroutineScope
) : TestWatcher() {

    private val dispatcher by lazy(dispatcherInitializer)
    val scope by lazy(scopeInitializer)

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        scope.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}