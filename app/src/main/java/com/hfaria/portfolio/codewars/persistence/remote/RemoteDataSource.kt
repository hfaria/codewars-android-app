package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.domain.User
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val api: CodeWarsApi
) {

    private fun <T> runCatching (call: () -> DataWrapper<T>): DataWrapper<T> {
        return try {
            call.invoke()
        } catch (t: Throwable) {
            DataWrapper.exception(t, null)
        }
    }

    fun getUserByUsername(username: String): DataWrapper<User>
        = runCatching { api.getUsers(username) }
}