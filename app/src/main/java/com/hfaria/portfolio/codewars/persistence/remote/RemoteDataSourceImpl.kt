package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.domain.User
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    val api: CodeWarsApi
) : RemoteDataSource {

    override fun getUserByUsername(username: String): ApiResponse<User>
        = api.getUsers(username)
}