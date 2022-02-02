package com.hfaria.portfolio.codewars.acceptance.search_user

import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.acceptance.setup.*
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.remote.RemoteErrorResponse
import com.hfaria.portfolio.codewars.persistence.remote.RemoteSuccessResponse
import com.hfaria.portfolio.codewars.persistence.remote.api.UserNetwork
import com.hfaria.portfolio.codewars.ui.search_user.SearchUserController
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class SearchUserAcceptanceTest : BaseAcceptanceTest() {

    override fun injectTest(component: TestAppComponent) = component.inject(this)

    @Inject
    lateinit var controller: SearchUserController

    @Inject
    lateinit var stubApi: StubCodeWarsApi

    @Inject
    lateinit var stubUserDao: StubUserDao

    lateinit var runner: TestRunner

    @Before
    fun setup() {
        runner = TestRunner(controller, stubApi)
    }

    class TestRunner(
        private val controller: SearchUserController,
        private val stubApi: StubCodeWarsApi
        ) {

        private val repositoryFailure = "A_FAILURE"
        private val repositoryException = "A_EXCEPTION"
        private lateinit var searchUsername: String
        private lateinit var user: UserNetwork
        private lateinit var loadingStates: List<Boolean>
        private lateinit var expectedErrorMessage: String

        fun givenUserExists(username: String) {
            user = UserNetwork(username, "")
            stubApi.getUserReponse = RemoteSuccessResponse(user)
        }

        fun givenRepositoryWillFail() {
            expectedErrorMessage = repositoryFailure
            stubApi.getUserReponse = RemoteErrorResponse(expectedErrorMessage)
        }

        fun givenRepositoryWillThrowException() {
            expectedErrorMessage = repositoryException
            stubApi.getUserException = Exception(expectedErrorMessage)
        }

        fun whenUsernameIsSearched(username: String) {
            searchUsername = username
            loadingStates = controller.state.isLoading.collect({
                controller.state.username.postValue(username)
                controller.handleUserSearch()
            })
        }

        fun thenAppShouldOpenUserProfileScreen() {
            val searchedUser = controller.routes.userProfileRoute.getSync()
            assertEquals(searchedUser.username, searchUsername)
            assertEquals(2, loadingStates.size)
            assertTrue(loadingStates[0])
            assertFalse(loadingStates[1])
        }

        fun thenAppShouldShowError(errorId: Int) {
            assertEquals(errorId, controller.state.errorId.getSync())
        }

        fun thenAppShouldShowError() {
            assertEquals(expectedErrorMessage, controller.state.errorMessage.getSync())
        }
    }

    @Test
    fun `SUCCESS - Should Route to User Profile Screen`() = runBlocking {
        val username = "g964"
        runner.givenUserExists(username)
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldOpenUserProfileScreen()
    }

    @Test
    fun `ERROR - Empty Username - Should show message to enter an username`() = runBlocking {
        val username = ""
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldShowError(R.string.error_empty_username)
    }

    @Test
    fun `ERROR - Short Username - Should show error message`() = runBlocking {
        val username = "ab"
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldShowError(R.string.error_short_username)
    }


    @Test
    fun `ERROR - Repository Error - Should show message explaining repository error`() = runBlocking {
        val username = "abcd"
        runner.givenRepositoryWillFail()
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldShowError()
    }

    @Test
    fun `EXCEPTION - Should show message explaining unexpected exception`() = runBlocking {
        val username = "abcd"
        runner.givenRepositoryWillThrowException()
        runner.whenUsernameIsSearched(username)
        runner.thenAppShouldShowError()
    }

}