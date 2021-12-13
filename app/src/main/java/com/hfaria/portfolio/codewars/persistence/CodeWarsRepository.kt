package com.hfaria.portfolio.codewars.persistence

import com.hfaria.portfolio.codewars.persistence.remote.RemoteDataSource
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.local.LocalDataSource
import javax.inject.Inject

class CodeWarsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {

    fun getUser(username: String): DataWrapper<User> =
        remoteDataSource.getUserByUsername(username)

    //suspend fun getRecentUsers(): DataWrapper<List<User>>
    //        = localDataSource.getRecentUsers()
}