package com.hfaria.portfolio.codewars.persistence.local

import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.Status
import com.hfaria.portfolio.codewars.persistence.local.dao.*
import com.hfaria.portfolio.codewars.persistence.local.db.AppDatabase
import com.hfaria.portfolio.codewars.persistence.local.entity.AuthoredChallengeEntity
import com.hfaria.portfolio.codewars.persistence.remote.api.AuthoredChallenges
import com.hfaria.portfolio.codewars.persistence.remote.api.ChallengeProfileEntity
import com.hfaria.portfolio.codewars.persistence.remote.api.User
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity
import com.hfaria.portfolio.codewars.util.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO cleanup this class. It's quite messy
class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val authoredChallengeDao: AuthoredChallengeDao,
    private val challengeProfileDao: ChallengeProfileDao,
    val completedChallengeDao: CompletedChallengeDao,
    val remoteKeysDao: RemoteKeysDao,
    val database: AppDatabase
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

    fun getAuthoredChallenges(username: String): DataWrapper<AuthoredChallenges> = run {
        val userWithChallenges = userDao.getAuthoredChallenges(username)
        if (userWithChallenges != null) {
            val challengeList = userWithChallenges.authoredChallenges.map { entity ->
                AuthoredChallengeEntity.toDomain(entity)
            }
            val challengesObj = AuthoredChallenges(username, challengeList)
            DataWrapper.success(challengesObj)
        } else {
            DataWrapper.error("Authored Challenges not found", null)
        }
    }

    fun saveAuthoredChallenges(authoredChallenges: AuthoredChallenges) = run {
        val author = authoredChallenges.author
        val entities = authoredChallenges.data.map { chal ->
            AuthoredChallengeEntity.fromDomain(author, chal)
        }
        authoredChallengeDao.deleteAllFromAuthor(author)
        authoredChallengeDao.insertAll(entities)

        /* Save AuthoredChallenges insertion time in User entity */
        val userEntity = userDao.getByUsername(author)
        userEntity!!.updatedAuthoredChallengesAt = TimeUtil.nowInSeconds()
        userDao.update(userEntity)
        DataWrapper.success(null)
    }

    fun saveChallengeProfile(challenge: ChallengeProfile) = run {
        val entity = ChallengeProfileEntity.fromDomain(challenge)
        challengeProfileDao.insert(entity)
        DataWrapper.success(null)
    }

    fun getChallengeProfileById(id : String): DataWrapper<ChallengeProfile> = run {
        val entity = challengeProfileDao.getById(id)
        if (entity != null) {
            val challenge = ChallengeProfileEntity.toDomain(entity)
            DataWrapper.success(challenge)
        } else {
            DataWrapper.error("ChallengeProfile not found", null)
        }
    }

    fun hasChallengeProfileCacheExpired(challenge: ChallengeProfile): Boolean {
        val timeNow = TimeUtil.nowInSeconds()
        val elapsed = timeNow - challenge.updatedAt
        return elapsed > 10
    }

    fun hasUserCacheExpired(user: User): Boolean {
        val timeNow = TimeUtil.nowInSeconds()
        val elapsed = timeNow - user.updatedAt
        return elapsed > 10
    }

    fun hasAuthoredChallengesExpired(authoredChallenges: AuthoredChallenges): Boolean {
        val userResponse = getUserByUsername(authoredChallenges.author)

        return if (userResponse.status == Status.SUCCESS) {
            val timeNow = TimeUtil.nowInSeconds()
            val elapsed = timeNow - userResponse.data!!.updatedAuthoredChallengesAt
            elapsed > 10
        } else {
            true
        }
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