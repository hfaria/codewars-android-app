package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.Status
import com.hfaria.portfolio.codewars.persistence.network.api.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchUserViewModel @Inject constructor(
    private val repository: CodeWarsRepository
) : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()

    val user: LiveData<User>
        get() = _user
    private val _user = MutableLiveData<User>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val _errorMessage = MutableLiveData<String>()

    fun onSearchRequested() {
        viewModelScope.launch {
            repository.getUser(username.value!!)
                .onStart { _isLoading.value = true }
                .onCompletion { _isLoading.value = false }
                .collect { response ->
                    if (response != null) {
                        if (response.status == Status.SUCCESS) {
                            _user.value = response.data!!
                        } else {
                            _errorMessage.value = response.message!!
                        }
                    }
                }
        }
    }

}