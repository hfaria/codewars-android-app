package com.hfaria.portfolio.codewars.acceptance.search_user

import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.test_setup.*
import com.hfaria.portfolio.codewars.ui.search_user.NewSearchUserViewModel
import com.hfaria.portfolio.codewars.ui.search_user.SearchUserScreenState
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

        //Given
        val user = User(username, null, 0, null, 0)
        stubApi.getUserReponse = DataWrapper.success(user)

        // When
        viewModel.state.username.postValue(username)
        viewModel.handleUserSearch()

        // Then
        val searchedUser = viewModel.routes.userProfileRoute.getSync()
        assertEquals(searchedUser.username, username)
    }

    @Test
    fun `ERROR - Empty Username - Should show message to enter an username`() = runBlocking {
        // Given
        val username = ""

        // When
        viewModel.state.username.postValue(username)
        viewModel.handleUserSearch()

        // Then
        assertEquals(SearchUserScreenState.ERROR_EMPTY_USERNAME, viewModel.state.errorMessage.getSync())
    }

    @Test
    fun `ERROR - Backend Error - Should show message explaining backend error`() = runBlocking {
        // Given
        val username = "abcd"
        viewModel.state.username.postValue(username)

        //Given
        stubApi.getUserReponse = DataWrapper.error(SearchUserScreenState.ERROR_BACKEND)

        // When
        viewModel.handleUserSearch()

        // Then
        assertEquals(SearchUserScreenState.ERROR_BACKEND, viewModel.state.errorMessage.getSync())
    }
}