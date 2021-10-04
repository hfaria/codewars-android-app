package com.hfaria.portfolio.codewars.ui.user_challenges

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.Status
import com.hfaria.portfolio.codewars.persistence.network.api.CompletedChallenge
import com.hfaria.portfolio.codewars.persistence.network.api.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserChallengesViewModel @Inject constructor(
    private val repository: CodeWarsRepository
) : ViewModel() {

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val _errorMessage = MutableLiveData<String>()

    val completedChallenges: LiveData<PagingData<CompletedChallenge>>
        get() = _completedChallenges
    private val _completedChallenges = MutableLiveData<PagingData<CompletedChallenge>>()

    fun fetchCompletedChallenges(username: String) {
        viewModelScope.launch {
            repository.getCompletedChallenges(username).collect { response ->
                _completedChallenges.value = response
            }
        }
    }
}