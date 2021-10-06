package com.hfaria.portfolio.codewars.persistence.local

import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.local.dao.*
import com.hfaria.portfolio.codewars.persistence.local.db.AppDatabase
import com.hfaria.portfolio.codewars.persistence.local.entity.AuthoredChallengeEntity
import com.hfaria.portfolio.codewars.persistence.remote.api.AuthoredChallenges
import com.hfaria.portfolio.codewars.persistence.remote.api.ChallengeProfileEntity
import com.hfaria.portfolio.codewars.persistence.remote.api.User
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity
import com.hfaria.portfolio.codewars.util.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val authoredChallengeDao: AuthoredChallengeDao,
    private val challengeProfileDao: ChallengeProfileDao,
    val completedChallengeDao: CompletedChallengeDao,
    val remoteKeysDao: RemoteKeysDao,
    val database: AppDatabase
) {

    protected suspend fun <T> runQuery(query: suspend () -> T) =
        query.runCatching { invoke() }
            .onFailure { t -> t.printStackTrace()}
            .getOrThrow()

    suspend fun saveUser(user: User) = runQuery {
        withContext(Dispatchers.IO) {
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

    suspend fun hasUserCacheExpired(user: User): Boolean {
        val timeNow = TimeUtil.nowInSeconds()
        val elapsed = timeNow - user.updatedAt
        return elapsed > 10
    }

    suspend fun getAuthoredChallenges(username: String): DataWrapper<AuthoredChallenges> = runQuery {
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

    suspend fun saveAuthoredChallenges(authoredChallenges: AuthoredChallenges) = runQuery {
        withContext(Dispatchers.IO) {
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
        }
    }

    suspend fun hasAuthoredChallengesExpired(authoredChallenges: AuthoredChallenges): Boolean = runQuery {
        val user = userDao.getByUsername(authoredChallenges.author)
        if (user != null) {
            val timeNow = TimeUtil.nowInSeconds()
            val elapsed = timeNow - user.updatedAuthoredChallengesAt
            elapsed > 10
        } else {
            true
        }
    }

    suspend fun saveChallengeProfile(challenge: ChallengeProfile) = runQuery {
        withContext(Dispatchers.IO) {
            val entity = ChallengeProfileEntity.fromDomain(challenge)
            challengeProfileDao.insert(entity)
        }
    }

    suspend fun getChallengeProfileById(id : String): DataWrapper<ChallengeProfile> = runQuery {
        val entity = challengeProfileDao.getById(id)
        if (entity != null) {
            val challenge = ChallengeProfileEntity.toDomain(entity)
            DataWrapper.success(challenge)
        } else {
            DataWrapper.error("ChallengeProfile not found", null)
        }
    }

    suspend fun hasChallengeProfileCacheExpired(challenge: ChallengeProfile): Boolean {
        val timeNow = TimeUtil.nowInSeconds()
        val elapsed = timeNow - challenge.updatedAt
        return elapsed > 10
    }

}