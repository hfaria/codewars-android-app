package com.hfaria.portfolio.codewars.acceptance.search_user

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
    fun test_search_user_should_route_to_user_profile_screen() = runBlocking {
        // Given
        val username = "g964"
        viewModel.username.postValue(username)

        // When
        viewModel.handleUserSearch()

        // Then
        val user = viewModel.userProfileRoute.getSync()
        assertEquals(user.username, username)
    }
}