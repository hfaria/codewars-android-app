package com.hfaria.portfolio.codewars.persistence

import com.hfaria.portfolio.codewars.persistence.remote.RemoteDataSource
import com.hfaria.portfolio.codewars.domain.User
import javax.inject.Inject

class CodeWarsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    fun getUser(username: String): DataWrapper<User> =
        remoteDataSource.getUserByUsername(username)

    //suspend fun getRecentUsers(): DataWrapper<List<User>>
    //        = localDataSource.getRecentUsers()
}