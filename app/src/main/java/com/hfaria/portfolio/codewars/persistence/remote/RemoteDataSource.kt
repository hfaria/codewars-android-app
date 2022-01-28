package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.remote.adapter.ApiResponse

interface RemoteDataSource {
    fun getUserByUsername(username: String): ApiResponse<User>
}