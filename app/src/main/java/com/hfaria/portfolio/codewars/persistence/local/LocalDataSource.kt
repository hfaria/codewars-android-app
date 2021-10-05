package com.hfaria.portfolio.codewars.persistence.local

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.local.dao.UserDao
import com.hfaria.portfolio.codewars.persistence.remote.api.User
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity
import com.hfaria.portfolio.codewars.util.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao
) {

    protected suspend fun <T> runQuery(query: suspend () -> T) =
        query.runCatching { invoke() }
            .onFailure { t -> t.printStackTrace()}
            .getOrThrow()

    suspend fun saveUser(userWrapper: DataWrapper<User>) = runQuery {
        withContext(Dispatchers.IO) {
            val user = userWrapper.data!!
            val entity = UserEntity.fromDomain(user)
            userDao.insert(entity)
        }
    }

    suspend fun getUserByUsername(username: String): DataWrapper<User> = runQuery {
        val entity = userDao.getByUsername(username)
        if (entity != null) {
            val user = UserEntity.toDomain(entity)
            DataWrapper.success(user)
        } else {
            DataWrapper.error("User not found", null)
        }
    }

    suspend fun getRecentUsers(): Flow<List<User>> {
        return flow {
            val users: List<User>

            withContext(Dispatchers.IO) {
                users = userDao.getAll().map {
                    UserEntity.toDomain(it)
                }
            }

            emit(users)
        }
    }

    fun hasUserCacheExpired(userWrapper: DataWrapper<User>): Boolean {
        return if (userWrapper.hasData()) {
            val user = userWrapper.data!!
            val timeNow = TimeUtil.nowInSeconds()
            val elapsed = timeNow - user.searchTime
            elapsed > 10
        } else {
            true
        }
    }
}