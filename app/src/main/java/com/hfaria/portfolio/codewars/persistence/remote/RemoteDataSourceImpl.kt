package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.remote.adapter.ApiEmptyResponse
import com.hfaria.portfolio.codewars.persistence.remote.adapter.ApiErrorResponse
import com.hfaria.portfolio.codewars.persistence.remote.adapter.ApiNotFoundResponse
import com.hfaria.portfolio.codewars.persistence.remote.adapter.ApiSuccessResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    val api: CodeWarsApi
) : RemoteDataSource {

    private fun <T> runCatching (call: () -> DataWrapper<T>): DataWrapper<T> {
        return try {
            call.invoke()
        } catch (t: Throwable) {
            DataWrapper.exception(t, null)
        }
    }

    override fun getUserByUsername(username: String): DataWrapper<User>
            = runCatching {
        val response = api.getUsers(username)
        when(response) {
            is ApiSuccessResponse -> DataWrapper.success(response.body)
            is ApiEmptyResponse -> DataWrapper.error("Empty Response", null)
            is ApiErrorResponse -> DataWrapper.error(response.errorMessage, null)
            is ApiNotFoundResponse -> DataWrapper.error("Not Found", null)
        }
    }
}