package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.Status
import com.hfaria.portfolio.codewars.domain.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewSearchUserViewModel @Inject constructor(
    private val repository: CodeWarsRepository
) : ViewModel() {

    val username = MutableLiveData<String>()

    val userProfileRoute: LiveData<User>
        get() = _userProfileRoute
    private val _userProfileRoute = MutableLiveData<User>()

    fun handleUserSearch() {
        viewModelScope.launch {
            val response = repository.getUser(username.value!!)
            if (response.status == Status.SUCCESS) {
                _userProfileRoute.value = response.data!!
            } else {


            }
        }
    }
}
