package com.hfaria.portfolio.codewars.persistence.local

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.local.dao.*
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity
import com.hfaria.portfolio.codewars.util.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
) {

    private fun <T> run(call: () -> DataWrapper<T>) =
        call.runCatching { invoke() }
            .onFailure { t -> t.printStackTrace()}
            .getOrThrow()

    fun saveUser(user: User) = run {
        val entity = UserEntity.fromDomain(user)
        userDao.insert(entity)
        DataWrapper.success(null)
    }

    fun getUserByUsername(username: String): DataWrapper<User> = run {
        val entity = userDao.getByUsername(username)
        if (entity != null) {
            val user = UserEntity.toDomain(entity)
            DataWrapper.success(user)
        } else {
            DataWrapper.error("User not found", null)
        }
    }

    fun hasUserCacheExpired(user: User): Boolean {
        val timeNow = TimeUtil.nowInSeconds()
        val elapsed = timeNow - user.updatedAt
        return elapsed > 10
    }

    suspend fun getRecentUsers(): DataWrapper<List<User>> = withContext(Dispatchers.IO) {
        run {
            userDao.deleteAllButLast(5)
            val users = userDao.getAll().map {
                UserEntity.toDomain(it)
            }
            DataWrapper.success(users)
        }
    }
}