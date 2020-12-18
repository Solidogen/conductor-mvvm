package com.example.conductormvvm.modules

import com.example.conductormvvm.util.utils.IAppDispatchers
import com.example.conductormvvm.util.utils.observable.ErrorManager
import com.example.conductormvvm.util.utils.observable.NavigationManager
import com.example.conductormvvm.utils.TestAppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.koin.dsl.module

val baseTestModule = module {
    single { TestCoroutineDispatcher() }
    single { TestCoroutineScope(get<TestCoroutineDispatcher>()) } // for MainCoroutineRule
    single<CoroutineScope> { get<TestCoroutineScope>() } // for globalScope injection in repos etc
    single<IAppDispatchers> { TestAppDispatchers(testCoroutineDispatcher = get()) }
    single { ErrorManager(globalScope = get()) }
    single { NavigationManager(globalScope = get()) }
}