package com.hfaria.portfolio.codewars.persistence

import androidx.lifecycle.LiveData
import com.hfaria.portfolio.codewars.persistence.network.api.User

interface Repository {

    fun getUser(username: String) : LiveData<Resource<User>>
}