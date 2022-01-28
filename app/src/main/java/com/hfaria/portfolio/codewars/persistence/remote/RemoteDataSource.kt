package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.domain.User

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
}

/**
 *  separate class for HTTP 204 responses
 *  so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()
class ApiNotFoundResponse<T>() : ApiResponse<T>()
class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

interface RemoteDataSource {
    fun getUserByUsername(username: String): ApiResponse<User>
}