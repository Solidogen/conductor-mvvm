package com.example.conductormvvm.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.example.conductormvvm.data.domain.HomeData
import com.example.conductormvvm.data.response.HomeDataResponse
import com.example.conductormvvm.data.response.NewsDataResponse
import com.example.conductormvvm.data.response.ShopDataResponse
import com.example.conductormvvm.datasource.IHomeRemoteDataSource
import com.example.conductormvvm.repository.HomeRepository
import com.example.conductormvvm.ui.features.home.HomeViewModel
import com.example.conductormvvm.ui.features.main.MainViewModel
import com.example.conductormvvm.util.utils.*
import com.example.conductormvvm.util.utils.observable.ErrorManager
import com.example.conductormvvm.util.utils.observable.NavigationManager
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

val mockedHomeRemoteDataSource = mockk<IHomeRemoteDataSource>(relaxed = true)

// todo mockk mocks
val testModule = module {
    single<CoroutineScope> { TestCoroutineScope() } // maybe this is shit
    single<CoroutineDispatcher> { TestCoroutineDispatcher() }
    single<IAppDispatchers> {
        object : IAppDispatchers {
            override val main: CoroutineDispatcher = get()
            override val io: CoroutineDispatcher = get()
            override val default: CoroutineDispatcher = get()
        }
    }
    single { mockedHomeRemoteDataSource }
    single { HomeRepository(homeRemoteDataSource = get(), appDispatchers = get()) }
    single { ErrorManager(globalScope = get()) }
    single { NavigationManager(globalScope = get()) }
    viewModel {
        HomeViewModel(
            homeRepository = get(),
            errorManager = get(),
            navigationManager = get()
        )
    }
    viewModel {
        MainViewModel(
            webSocketRepository = mockk(),
            errorManager = get(),
            navigationManager = get()
        )
    }
}

/**
 * Test flow/sharedflow/navigation/livedata/viewmodelscope/appdispatchers test inject/test globalscope inject
 * */
class HomeViewModelTest : KoinTest {

    private val viewModel: HomeViewModel by inject()
    private val appDispatchers: IAppDispatchers by inject()
    private val mainViewModel: MainViewModel by inject()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // todo main rule again

    @Before
    fun beforeTest() {
        startKoin { modules(testModule) }
        Dispatchers.setMain(appDispatchers.main)
    }

    @After
    fun afterTest() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `test navigation to settings`() {
        runBlocking {
            mainViewModel.toString()
            viewModel.navigate(NavDestination.Settings)
            delay(1000)
            println("JESTEM TU")
            assertThat(mainViewModel.navDestination.asFlow().first(), equalTo(NavDestination.Settings))
        }
    }

    @Test
    fun `test success emitted on successful api call`() {
        coEvery { mockedHomeRemoteDataSource.getHomeData() } returns HomeDataResponse(1, 1, "1", true)
        runBlocking {
            assertThat(viewModel.homeData.asFlow().first(), equalTo(HomeData(1, 1, "1", true)))
        }
    }

    @Test
    fun `test error emitted on failed api call`() {
        coEvery { mockedHomeRemoteDataSource.getHomeData() } throws RemoteException("Error", StatusCode.InternalServerError)
        runBlocking {
            assertThat(viewModel.homeData.value, equalTo(null))
        }
    }
}
