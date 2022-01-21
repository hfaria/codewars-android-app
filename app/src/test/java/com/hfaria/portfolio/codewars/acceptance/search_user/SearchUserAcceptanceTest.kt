package com.hfaria.portfolio.codewars.acceptance.search_user

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.test_setup.*
import com.hfaria.portfolio.codewars.ui.search_user.NewSearchUserViewModel
import com.hfaria.portfolio.codewars.ui.search_user.SearchUserOutput
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import javax.inject.Inject

class SearchUserAcceptanceTest : BaseAcceptanceTest() {

    override fun injectTest(component: TestAppComponent) = component.inject(this)

    @Inject
    lateinit var viewModel: NewSearchUserViewModel

    @Inject
    lateinit var stubApi: StubCodeWarsApi

    @Inject
    lateinit var stubUserDao: StubUserDao

    class TestRunner(
        val viewModel: NewSearchUserViewModel,
        val stubApi: StubCodeWarsApi
        ) {

        lateinit var searchUsername: String
        lateinit var user: User

        fun givenUserExists(username: String) {
            val user = User(username, null, 0, null)
            stubApi.getUserReponse = DataWrapper.success(user)
        }

        fun givenRepositoryWillFail(errorMessage: String) {
            stubApi.getUserReponse = DataWrapper.error(errorMessage)
        }

        fun givenRepositoryWillThrowException(exceptionMessage: String) {
            stubApi.getUserException = Exception(exceptionMessage)
        }

        fun whenUsernameIsSearched(username: String) {
            searchUsername = username
            viewModel.state.username.postValue(username)
            viewModel.handleUserSearch()
        }

        fun thenAppShouldOpenUserProfileScreen() {
            val searchedUser = viewModel.routes.userProfileRoute.getSync()
            assertEquals(searchedUser.username, searchUsername)
        }

        fun thenAppShouldShowError(error: String) {
            assertEquals(error, viewModel.state.errorMessage.getSync())
        }
    }

    @Test
    fun `SUCCESS - Should Route to User Profile Screen`() = runBlocking {
        val runner = TestRunner(viewModel, stubApi)
        val username = "g964"
        runner.givenUserExists(username)
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldOpenUserProfileScreen()
    }

    @Test
    fun `ERROR - Empty Username - Should show message to enter an username`() = runBlocking {
        val runner = TestRunner(viewModel, stubApi)
        val username = ""
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldShowError(SearchUserOutput.ERROR_EMPTY_USERNAME)
    }

    @Test
    fun `ERROR - Short Username - Should show error message`() = runBlocking {
        val runner = TestRunner(viewModel, stubApi)
        val username = "ab"
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldShowError(SearchUserOutput.ERROR_SHORT_USERNAME)
    }


    @Test
    fun `ERROR - Repository Error - Should show message explaining repository error`() = runBlocking {
        val runner = TestRunner(viewModel, stubApi)
        val username = "abcd"
        val errorMessage = "AN_ERROR"
        runner.givenRepositoryWillFail(errorMessage)
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldShowError(errorMessage)
    }

    @Test
    fun `EXCEPTION - Should show message explaining unexpected exception`() = runBlocking {
        val runner = TestRunner(viewModel, stubApi)
        val username = "abcd"
        val exceptionMessage = "A_EXCEPTION"
        runner.givenRepositoryWillThrowException(exceptionMessage)
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldShowError(exceptionMessage)
    }

}