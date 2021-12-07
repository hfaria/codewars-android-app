package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.Status
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchUserRoutes {
    val userProfileRoute: LiveData<User>
        get() = _userProfileRoute
    val _userProfileRoute = MutableLiveData<User>()
}

class SearchUserScreenState {

    val username = MutableLiveData<String>()

    val errorMessage: LiveData<Int>
        get() = _errorMessage
    val _errorMessage = MutableLiveData<Int>()
}

class NewSearchUserViewModel @Inject constructor(
    private val repository: CodeWarsRepository,
) : ViewModel() {

    val state = SearchUserScreenState()
    val routes = SearchUserRoutes()

    fun handleUserSearch() {
        val username = state.username.value

        if (username.isNullOrEmpty()) {
            state._errorMessage.value = R.string.error_empty_username
            return
        }

        searchUser(username)
    }

    private fun searchUser(username: String) {
        viewModelScope.launch {
            val response = repository.getUser(username)
            if (response.status == Status.SUCCESS) {
                routes._userProfileRoute.value = response.data!!
            } else {


            }
        }
    }
}
