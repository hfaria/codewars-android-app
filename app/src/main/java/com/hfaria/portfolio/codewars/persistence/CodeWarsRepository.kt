package com.hfaria.portfolio.codewars.persistence

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.local.LocalDataSource
import com.hfaria.portfolio.codewars.persistence.remote.*
import javax.inject.Inject

class CodeWarsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {

    fun getUser(username: String): DataWrapper<User> {
        val response = remoteDataSource.getUserByUsername(username)
        return when(response) {
            is ApiSuccessResponse -> DataWrapper.success(response.body)
            is ApiEmptyResponse -> DataWrapper.error("Empty Response", null)
            is ApiErrorResponse -> DataWrapper.error(response.errorMessage, null)
            is ApiNotFoundResponse -> DataWrapper.error("Not Found", null)
        }
    }

    //suspend fun getRecentUsers(): DataWrapper<List<User>>
    //        = localDataSource.getRecentUsers()
}