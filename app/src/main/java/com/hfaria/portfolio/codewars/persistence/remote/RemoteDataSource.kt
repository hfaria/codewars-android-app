package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.domain.User

@Suppress("unused") // T is used in extending classes
sealed class RemoteResponse<T>

class RemoteEmptyResponse<T> : RemoteResponse<T>()
class RemoteNotFoundResponse<T> : RemoteResponse<T>()
class RemoteErrorResponse<T>(val errorMessage: String) : RemoteResponse<T>()
class RemoteSuccessResponse<T>(val body: T) : RemoteResponse<T>()

interface RemoteDataSource {
    fun getUserByUsername(username: String): RemoteResponse<User>
}