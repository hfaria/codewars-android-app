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

    val recentUsers: LiveData<Array<User>>
        get() = _recentUsers
    private val _recentUsers = MutableLiveData<Array<User>>()

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
                    if (response.status == Status.SUCCESS) {
                        fetchRecentUsers()
                    } else {
                        _errorMessage.value = response.message!!
                    }
                }
        }
    }

    fun fetchRecentUsers() {
        viewModelScope.launch {
            repository.getRecentUsers().collect { response ->
                _recentUsers.value = response
            }
        }
    }
}