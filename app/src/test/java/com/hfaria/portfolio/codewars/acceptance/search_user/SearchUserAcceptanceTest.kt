package com.hfaria.portfolio.codewars.acceptance.search_user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hfaria.portfolio.codewars.test_setup.DaggerTestAppComponent
import com.hfaria.portfolio.codewars.test_setup.TestAppComponent
import com.hfaria.portfolio.codewars.test_setup.TestCodeWarsApp
import com.hfaria.portfolio.codewars.test_setup.getSync
import com.hfaria.portfolio.codewars.ui.search_user.NewSearchUserViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchUserAcceptanceTest {

    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var viewModel: NewSearchUserViewModel

    @Before
    fun setup() {
        val application = TestCodeWarsApp()
        val component: TestAppComponent = DaggerTestAppComponent
            .builder()
            .application(application)
            .build()
        component.inject(this)
    }

    @Test
    fun test_search_user_should_route_to_user_profile_screen() = runBlockingTest {
        val username = "g964"
        viewModel.username.postValue(username)
        viewModel.handleUserSearch()
        val user = viewModel.userProfileRoute.getSync()
        assertEquals(user.username, username)
    }
}