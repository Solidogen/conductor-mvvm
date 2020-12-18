package com.example.conductormvvm.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.conductormvvm.modules.baseTestModule
import com.example.conductormvvm.rules.MainCoroutineScopeRule
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.rules.RuleChain
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.test.KoinTestRule

/**
 * Testing all-in-one tool. Covers ViewModel/LiveData/Flow testing
 * */
class TestSuite(extraModules: List<Module> = emptyList()) : KoinComponent {

    private val mainCoroutineScopeRule = MainCoroutineScopeRule(
        dispatcherInitializer = { get() },
        scopeInitializer = { get() }
    )

    val scope: TestCoroutineScope by lazy {
        mainCoroutineScopeRule.scope
    }

    val ruleChain: RuleChain = RuleChain
        .outerRule(KoinTestRule.create { modules(baseTestModule + extraModules) })
        .around(InstantTaskExecutorRule())
        .around(mainCoroutineScopeRule)
}