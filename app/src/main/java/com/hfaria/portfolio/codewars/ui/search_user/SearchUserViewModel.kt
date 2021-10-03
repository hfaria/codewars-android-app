package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.Resource
import com.hfaria.portfolio.codewars.persistence.network.api.User
import javax.inject.Inject

class SearchUserViewModel @Inject constructor(
    repository: CodeWarsRepository
) : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    private val searchRequested: MutableLiveData<Boolean> = MutableLiveData()

    fun onSearchRequested() {
        searchRequested.postValue(true)
    }

    val user: LiveData<Resource<User>> = searchRequested.switchMap {
        repository.getUser(username.value!!)
    }
}