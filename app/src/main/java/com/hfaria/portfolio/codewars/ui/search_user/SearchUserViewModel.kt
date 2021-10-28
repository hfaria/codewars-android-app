package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.Status
import com.hfaria.portfolio.codewars.persistence.remote.api.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchUserViewModel @Inject constructor(
    private val repository: CodeWarsRepository
) : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()

    val orderByRankChecked: MutableLiveData<Boolean> = MutableLiveData()

    val searchedUser: LiveData<User>
        get() = _searchedUser
    private val _searchedUser = MutableLiveData<User>()

    val recentUsers: LiveData<List<User>>
        get() = _recentUsers
    private val _recentUsers = MutableLiveData<List<User>>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val _errorMessage = MutableLiveData<String>()

    fun onSearchRequested() {
        if (username.value.isNullOrEmpty()) {
            return
        }

        var publishedUser = false
        viewModelScope.launch {
            repository.getUser(username.value!!)
                .onStart { _isLoading.value = true }
                .onCompletion { _isLoading.value = false }
                .collect { response ->
                    if (response.status != Status.SUCCESS) {
                        _errorMessage.value = response.message!!
                    } else {
                        fetchRecentUsers()

                        if (!publishedUser) {
                            _searchedUser.value = response.data!!
                            publishedUser = true
                        }
                    }
                }
        }
    }

    fun fetchRecentUsers() {
        viewModelScope.launch {
            val response = repository.getRecentUsers()

            if (response.status == Status.SUCCESS) {
                _recentUsers.value = response.data!!.sortedByDescending { it.updatedAt }
            } else {
                _errorMessage.value = response.message!!
            }
        }
    }

    fun handleOrderByRankRequest() {
        val recentUsers = _recentUsers.value!!
        val checked = orderByRankChecked.value ?: false

        if (checked) {
            _recentUsers.value = recentUsers.sortedByDescending { it.ranks.overall.score }
        } else {
            _recentUsers.value = recentUsers.sortedByDescending { it.updatedAt }
        }
    }
}