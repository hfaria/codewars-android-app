package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.domain.User
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val api: CodeWarsApi
) {

    private fun <T> run(call: () -> DataWrapper<T>) =
        call.runCatching { invoke() }
            .onFailure { t ->  DataWrapper.exception(t, null)}
            .getOrThrow()

    fun getUserByUsername(username: String): DataWrapper<User>
        = run { api.getUsers(username) }
}