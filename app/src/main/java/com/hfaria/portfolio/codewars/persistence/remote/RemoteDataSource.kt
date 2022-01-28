package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.DataWrapper

interface RemoteDataSource {
    fun getUserByUsername(username: String): DataWrapper<User>
}