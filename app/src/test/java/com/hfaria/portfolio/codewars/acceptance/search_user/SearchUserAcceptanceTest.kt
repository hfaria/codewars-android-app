package com.hfaria.portfolio.codewars.acceptance.search_user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hfaria.portfolio.codewars.test_setup.DaggerTestAppComponent
import com.hfaria.portfolio.codewars.test_setup.TestAppComponent
import com.hfaria.portfolio.codewars.test_setup.TestCodeWarsApp
import com.hfaria.portfolio.codewars.test_setup.getSync
import com.hfaria.portfolio.codewars.ui.search_user.NewSearchUserViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class SearchUserAcceptanceTest {

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
    fun test_search_user_should_route_to_user_profile_screen() = runBlocking {
        val username = "g964"
        viewModel.username.postValue(username)
        viewModel.handleUserSearch()
        val bundle = viewModel.userProfileRoute.getSync()
        assertEquals(bundle.getString("username"), username)
    }
}