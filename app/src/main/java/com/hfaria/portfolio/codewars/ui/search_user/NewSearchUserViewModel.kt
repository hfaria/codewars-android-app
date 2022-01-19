package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.ui.search_user.interactor.InteractorOutput
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
        viewModelScope.launch {
            val output = searchUserInteractor.run(state.username.value)
            handleOutput(output)
        }
    }

    private fun handleOutput(output: InteractorOutput<User>) {
        when (output.status ) {
            InteractorOutput.Status.SUCCESS -> {
                routes._userProfileRoute.value = output.data!!
            }
            InteractorOutput.Status.EXCEPTION,
            InteractorOutput.Status.ERROR -> {
                state._errorMessage.value = output.message
            }
        }
    }
}
