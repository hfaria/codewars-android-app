package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.ui.search_user.interactor.InteractorOutput
import com.hfaria.portfolio.codewars.ui.search_user.interactor.SearchUserInteractor
import com.hfaria.portfolio.codewars.ui.search_user.interactor.SearchUserInteractor.UsernameValidation
import com.hfaria.portfolio.codewars.ui.search_user.interactor.SearchUserInteractor.UsernameValidation.EMPTY_USERNAME
import com.hfaria.portfolio.codewars.ui.search_user.interactor.SearchUserInteractor.UsernameValidation.SHORT_USERNAME
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchUserRoutes {
    val userProfileRoute: LiveData<User>
        get() = _userProfileRoute
    private val _userProfileRoute = MutableLiveData<User>()

    fun routeToUserProfileScreen(user: User) {
        _userProfileRoute.value = user
    }
}

class SearchUserScreenState {

    val username = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val _errorMessage = MutableLiveData<String>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    fun showErrorMessage(message: String) {
        _errorMessage.value = message
    }

    fun setLoadingState(state: Boolean) {
        _isLoading.value = state
    }

}

class SearchUserOutput(
    private val state: SearchUserScreenState,
    private val routes: SearchUserRoutes): InteractorOutput<User, SearchUserInteractor.UsernameValidation> {

    companion object {
        const val ERROR_EMPTY_USERNAME = "Please, enter an username"
        const val ERROR_SHORT_USERNAME = "This username is too short"
    }

    override fun onSuccess(data: User) {
        routes.routeToUserProfileScreen(data)
    }

    override fun onInvalidInput(reason: UsernameValidation) {
        when(reason) {
            EMPTY_USERNAME -> {
                state.showErrorMessage(ERROR_EMPTY_USERNAME)
            }
            SHORT_USERNAME -> {
                state.showErrorMessage(ERROR_SHORT_USERNAME)
            }
        }
    }

    override fun onRepositoryError(error: String) {
        state.showErrorMessage(error)
    }

    override fun onException(throwable: Throwable) {
        state.showErrorMessage(throwable.toString())
    }

    override fun onStartLoading() {
        state.setLoadingState(true)
    }

    override fun onStopLoading() {
        state.setLoadingState(false)
    }
}

class NewSearchUserViewModel @Inject constructor(
    private val searchUserInteractor: SearchUserInteractor,
) : ViewModel() {

    val state = SearchUserScreenState()
    val routes = SearchUserRoutes()
    private val searchUserOutput = SearchUserOutput(state, routes)

    fun handleUserSearch() {
        viewModelScope.launch {
            searchUserInteractor.run(state.username.value.orEmpty(), searchUserOutput)
        }
    }
}
