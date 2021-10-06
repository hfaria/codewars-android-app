package com.hfaria.portfolio.codewars.ui.challenge_profile

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChallengeProfileViewModel @Inject constructor(
    private val repository: CodeWarsRepository
) : ViewModel() {

    val profile: LiveData<ChallengeProfile>
        get() = _profile
    private val _profile = MutableLiveData<ChallengeProfile>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    val errorMessage: LiveData<String>
        get() = _errorMessage
    private val _errorMessage = MutableLiveData<String>()

    fun fetchProfile(profileId: String) {
        viewModelScope.launch {
            repository.getChallengeProfile(profileId)
                .onStart { _isLoading.value = true }
                .onCompletion { _isLoading.value = false }
                .collect { response ->
                    if (response.status == Status.SUCCESS) {
                        _profile.value = response.data!!
                    } else {
                        _errorMessage.value = response.message!!
                    }
                }
        }
    }
}