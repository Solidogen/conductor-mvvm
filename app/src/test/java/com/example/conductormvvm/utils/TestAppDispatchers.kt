package com.example.conductormvvm.utils

import com.example.conductormvvm.util.utils.IAppDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestAppDispatchers(testCoroutineDispatcher: TestCoroutineDispatcher): IAppDispatchers {
    override val main: CoroutineDispatcher = testCoroutineDispatcher
    override val io: CoroutineDispatcher = testCoroutineDispatcher
    override val default: CoroutineDispatcher = testCoroutineDispatcher
}