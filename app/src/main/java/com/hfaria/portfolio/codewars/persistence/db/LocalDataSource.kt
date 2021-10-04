package com.hfaria.portfolio.codewars.persistence.db

import android.util.Log
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.Status
import com.hfaria.portfolio.codewars.persistence.network.api.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao
) {

    protected suspend fun <T> runQuery(query: suspend () -> T) =
        query.runCatching { invoke() }
            .onFailure { t -> t.printStackTrace()}
            .getOrThrow()

    private fun timeInSeconds() : Int {
        val timeInMillis = System.currentTimeMillis()
        return TimeUnit.MILLISECONDS.toSeconds(timeInMillis).toInt()
    }

    suspend fun saveUser(userWrapper: DataWrapper<User>) = runQuery {
        withContext(Dispatchers.IO) {
            val user = userWrapper.data!!

            if (user.name == null || user.name.isEmpty()) {
                user.name = "Unknown"
            }

            user.insertionTime = timeInSeconds()
            userDao.insert(user)
        }
    }

    suspend fun getUserByUsername(username: String): DataWrapper<User> = runQuery {
        val user = userDao.getByUsername(username)
        if (user != null) {
            DataWrapper.success(user)
        } else {
            DataWrapper.error("User not found", null)
        }
    }

    suspend fun getRecentUsers(): Flow<Array<User>> {
        return flow {
            val users: Array<User>

            withContext(Dispatchers.IO) {
                users = userDao.getAll()
            }

            emit(users)
        }
    }

    fun hasUserCacheExpired(userWrapper: DataWrapper<User>): Boolean {
        return if (userWrapper.hasData()) {
            val user = userWrapper.data!!
            val timeNow = timeInSeconds()
            val elapsed = timeNow - user.insertionTime
            elapsed > 10
        } else {
            true
        }
    }
}