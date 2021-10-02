package com.hfaria.portfolio.codewars.ui.search_user

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.persistence.RepositoryImpl
import com.hfaria.portfolio.codewars.persistence.Resource
import com.hfaria.portfolio.codewars.persistence.network.api.User

class SearchUserViewModel : ViewModel() {

    val repository : RepositoryImpl = RepositoryImpl()

    val username: MutableLiveData<String> = MutableLiveData()

    val user: LiveData<Resource<User>> = username.switchMap { uname ->
        repository.getUser(uname)
    }

}