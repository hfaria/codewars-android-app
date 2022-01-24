package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.ui.base.BasePresenter
import com.hfaria.portfolio.codewars.ui.base.BaseScreenState
import com.hfaria.portfolio.codewars.domain.user.SearchUserInteractor
import com.hfaria.portfolio.codewars.domain.user.SearchUserInteractor.UsernameValidation
import com.hfaria.portfolio.codewars.domain.user.SearchUserInteractor.UsernameValidation.EMPTY_USERNAME
import com.hfaria.portfolio.codewars.domain.user.SearchUserInteractor.UsernameValidation.SHORT_USERNAME
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

class SearchUserScreenState: BaseScreenState() {
    val username = MutableLiveData<String>()
}

class SearchUserPresenter(
    state: SearchUserScreenState,
    private val routes: SearchUserRoutes): BasePresenter<User, UsernameValidation>(state) {

    override fun onSuccess(data: User) {
        routes.routeToUserProfileScreen(data)
    }

    override fun onInvalidInput(reason: UsernameValidation) {
        when(reason) {
            EMPTY_USERNAME -> {
                state.showErrorMessage(R.string.error_empty_username)
            }
            SHORT_USERNAME -> {
                state.showErrorMessage(R.string.error_short_username)
            }
        }
    }
}

class SearchUserController @Inject constructor(
    private val searchUserInteractor: SearchUserInteractor,
) : ViewModel() {

    val state = SearchUserScreenState()
    val routes = SearchUserRoutes()
    private val searchUserOutput = SearchUserPresenter(state, routes)

    fun handleUserSearch() {
        viewModelScope.launch {
            searchUserInteractor.run(state.username.value.orEmpty(), searchUserOutput)
        }
    }
}
