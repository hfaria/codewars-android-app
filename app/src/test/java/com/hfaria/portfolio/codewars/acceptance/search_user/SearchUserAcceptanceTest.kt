package com.hfaria.portfolio.codewars.acceptance.search_user

import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.test_setup.*
import com.hfaria.portfolio.codewars.ui.search_user.NewSearchUserViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import javax.inject.Inject

class SearchUserAcceptanceTest : BaseAcceptanceTest() {

    override fun injectTest(component: TestAppComponent) = component.inject(this)

    @Inject
    lateinit var viewModel: NewSearchUserViewModel

    @Inject
    lateinit var stubApi: StubCodeWarsApi

    @Test
    fun `SUCCESS - Should Route to User Profile Screen`() = runBlocking {
        // Given
        val username = "g964"
        viewModel.state.username.postValue(username)

        // When
        viewModel.handleUserSearch()

        // Then
        val user = viewModel.routes.userProfileRoute.getSync()
        assertEquals(user.username, username)
    }

    @Test
    fun `ERROR - Empty Username - Should show message to enter an username`() = runBlocking {
        // Given
        val username = ""
        viewModel.state.username.postValue(username)

        // When
        viewModel.handleUserSearch()

        // Then
        assertEquals(R.string.error_empty_username, viewModel.state.errorMessage.getSync())
    }
}