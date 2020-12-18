package com.example.conductormvvm.features.home

import androidx.lifecycle.asFlow
import app.cash.turbine.test
import com.example.conductormvvm.utils.TestSuite
import com.example.conductormvvm.data.domain.HomeData
import com.example.conductormvvm.data.response.HomeDataResponse
import com.example.conductormvvm.datasource.IHomeRemoteDataSource
import com.example.conductormvvm.repository.HomeRepository
import com.example.conductormvvm.ui.features.home.HomeViewModel
import com.example.conductormvvm.util.utils.ErrorType
import com.example.conductormvvm.util.utils.NavDestination
import com.example.conductormvvm.util.utils.RemoteException
import com.example.conductormvvm.util.utils.StatusCode
import com.example.conductormvvm.util.utils.observable.ErrorManager
import com.example.conductormvvm.util.utils.observable.NavigationManager
import com.example.conductormvvm.utils.forceInject
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
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
@ExperimentalCoroutinesApi
class HomeViewModelTest : KoinTest {

    private val viewModel: HomeViewModel by inject()
    private val navigationManager: NavigationManager by inject()
    private val errorManager: ErrorManager by inject()

    private val testSuite = TestSuite(listOf(homeTestModule))

    @get:Rule
    val ruleChain: RuleChain = testSuite.ruleChain

    @Test
    fun `navigation to settings emits navigation event`() {
        testSuite.scope.runBlockingTest {
            navigationManager.navDestination.test {
                viewModel.navigate(NavDestination.Settings)
                assertThat(expectItem().peekContent(), equalTo(NavDestination.Settings))
            }
        }
    }

    @Test
    fun `successful HomeData api call emits HomeData to subscribers`() {
        coEvery { mockedHomeRemoteDataSource.getHomeData() } returns successfulHomeDataResponse
        testSuite.scope.runBlockingTest {
            viewModel.homeData.asFlow().test {
                assertThat(expectItem(), equalTo(expectedHomeData))
            }
        }
    }

    @Test
    fun `failed HomeData api call does NOT emit home data`() {
        coEvery { mockedHomeRemoteDataSource.getHomeData() } throws internalServerException
        testSuite.scope.runBlockingTest {
            viewModel.homeData.asFlow().test {
                expectNoEvents()
            }
        }
    }

    @Test
    fun `failed HomeData api call emits error to subscribers`() {
        coEvery { mockedHomeRemoteDataSource.getHomeData() } throws internalServerException
        testSuite.scope.runBlockingTest {
            errorManager.errors.test {
                viewModel.forceInject()
                assertThat(expectItem().peekContent(), equalTo(internalServerErrorType))
            }
        }
    }

    /////////

    private val successfulHomeDataResponse = HomeDataResponse(
        userId = 1,
        id = 1,
        title = "1",
        completed = true
    )

    private val expectedHomeData = HomeData(
        userId = 1,
        id = 1,
        title = "1",
        completed = true
    )

    private val internalServerException = RemoteException(
        message = "Error",
        statusCode = StatusCode.InternalServerError
    )

    private val internalServerErrorType = ErrorType.RemoteError(
        message = "Error",
        statusCode = StatusCode.InternalServerError
    )
}
