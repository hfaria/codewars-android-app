package com.hfaria.portfolio.codewars.persistence

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.local.LocalDataSource
import com.hfaria.portfolio.codewars.persistence.remote.*
import com.hfaria.portfolio.codewars.persistence.remote.api.UserMapper
import javax.inject.Inject

class CodeWarsRepository @Inject constructor(
    private val remoteMapper: UserMapper,
    private val userRemote: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {

    fun getUser(username: String): DataWrapper<User> {
        val response = userRemote.get(username)
        return when(response) {
            is RemoteSuccessResponse -> DataWrapper.success(remoteMapper.map(response.body))
            is RemoteEmptyResponse -> DataWrapper.error("Empty Response", null)
            is RemoteErrorResponse -> DataWrapper.error(response.errorMessage, null)
            is RemoteNotFoundResponse -> DataWrapper.error("Not Found", null)
        }
    }

    //suspend fun getRecentUsers(): DataWrapper<List<User>>
    //        = localDataSource.getRecentUsers()
}