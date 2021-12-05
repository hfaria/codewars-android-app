package com.hfaria.portfolio.codewars.ui.search_user

import android.os.Bundle
import androidx.lifecycle.*
import javax.inject.Inject

class NewSearchUserViewModel @Inject constructor(
) : ViewModel() {

    val username = MutableLiveData<String>()

    val userProfileRoute: LiveData<Bundle>
        get() = _userProfileRoute
    private val _userProfileRoute = MutableLiveData<Bundle>()

    fun handleUserSearch() {

    }

}