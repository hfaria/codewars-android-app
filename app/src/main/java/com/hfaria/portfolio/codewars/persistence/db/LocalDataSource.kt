package com.hfaria.portfolio.codewars.persistence.db

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.network.api.User
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao
) {

    protected suspend fun <T> runQuery(query: suspend () -> T) =
        query.runCatching { invoke() }
            .onFailure { }
            .getOrThrow()

    suspend fun saveUser(userWrapper: DataWrapper<User>) = runQuery {
        userDao.insert(userWrapper.data!!)
    }

    suspend fun getUserByUsername(username: String): DataWrapper<User> = runQuery {
        val user = userDao.getByUsername(username)
        if (user != null) {
            DataWrapper.success(user)
        } else {
            DataWrapper.error("User not found", null)
        }
    }
}