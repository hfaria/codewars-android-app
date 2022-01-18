package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.Status
import com.hfaria.portfolio.codewars.ui.search_user.interactor.SearchUserInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchUserRoutes {
    val userProfileRoute: LiveData<User>
        get() = _userProfileRoute
    val _userProfileRoute = MutableLiveData<User>()
}

class SearchUserScreenState {

    companion object {
        const val ERROR_EMPTY_USERNAME = "Please, enter an username"
        const val ERROR_BACKEND = "GENERIC_BACKEND_ERROR"
    }

    val username = MutableLiveData<String>()

    val errorMessage: LiveData<String>
        get() = _errorMessage
    val _errorMessage = MutableLiveData<String>()

}

class NewSearchUserViewModel @Inject constructor(
    private val searchUserInteractor: SearchUserInteractor,
) : ViewModel() {

    val state = SearchUserScreenState()
    val routes = SearchUserRoutes()

    fun handleUserSearch() {
        searchUser(state.username.value)
    }

    private fun searchUser(username: String?) {
        viewModelScope.launch {
            val response = searchUserInteractor.run(username)
            handleResponse(response)
        }
    }

    private fun handleResponse(response: DataWrapper<User>) {
        if (response.status == Status.SUCCESS) {
            routes._userProfileRoute.value = response.data!!
        } else {
            state._errorMessage.value = response.message
        }
    }
}
