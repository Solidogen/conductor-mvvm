package com.example.conductormvvm.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import app.cash.turbine.test
import com.example.conductormvvm.data.domain.HomeData
import com.example.conductormvvm.data.response.HomeDataResponse
import com.example.conductormvvm.datasource.IHomeRemoteDataSource
import com.example.conductormvvm.modules.baseTestModule
import com.example.conductormvvm.repository.HomeRepository
import com.example.conductormvvm.rules.MainCoroutineScopeRule
import com.example.conductormvvm.ui.features.home.HomeViewModel
import com.example.conductormvvm.util.utils.IAppDispatchers
import com.example.conductormvvm.util.utils.NavDestination
import com.example.conductormvvm.util.utils.RemoteException
import com.example.conductormvvm.util.utils.StatusCode
import com.example.conductormvvm.util.utils.observable.ErrorManager
import com.example.conductormvvm.util.utils.observable.NavigationManager
import com.example.conductormvvm.utils.TestAppDispatchers
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.inject
import kotlin.time.ExperimentalTime

val mockedHomeRemoteDataSource = mockk<IHomeRemoteDataSource>(relaxed = true)

val homeTestModule = module {
    single { mockedHomeRemoteDataSource }
    single { HomeRepository(homeRemoteDataSource = get(), appDispatchers = get()) }
    viewModel {
        HomeViewModel(
            homeRepository = get(),
            errorManager = get(),
            navigationManager = get()
        )
    }
}

@ExperimentalTime
class HomeViewModelTest : KoinTest {

    private val viewModel: HomeViewModel by inject()
    private val navigationManager: NavigationManager by inject()
    private val errorManager: ErrorManager by inject()

    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(KoinTestRule.create {
            modules(baseTestModule, homeTestModule)
        })
        .around(InstantTaskExecutorRule())
        .around(
            MainCoroutineScopeRule(
                dispatcherInitializer = { get() },
                scopeInitializer = { get() }
            )
        )

    @Test
    fun `test navigation to settings emits n`() {
        runBlocking {
            navigationManager.navDestination.test {
                viewModel.navigate(NavDestination.Settings)
                assertThat(expectItem().peekContent(), equalTo(NavDestination.Settings))
            }
        }
    }

    @Test
    fun `test success emitted on successful api call`() {
        coEvery { mockedHomeRemoteDataSource.getHomeData() } returns HomeDataResponse(
            userId = 1,
            id = 1,
            title = "1",
            completed = true
        )
        runBlocking {
            viewModel.homeData.asFlow().test {
                assertThat(
                    expectItem(), equalTo(
                        HomeData(
                            userId = 1,
                            id = 1,
                            title = "1",
                            completed = true
                        )
                    )
                )
            }
        }
    }

    @Test
    fun `test error emitted on failed api call`() {
        coEvery { mockedHomeRemoteDataSource.getHomeData() } throws RemoteException(
            "Error",
            StatusCode.InternalServerError
        )
        runBlocking {
            viewModel.homeData.asFlow().test {
                expectNoEvents()
            }
        }
    }
}
