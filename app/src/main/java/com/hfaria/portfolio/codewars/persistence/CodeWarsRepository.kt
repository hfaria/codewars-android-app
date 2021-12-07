package com.hfaria.portfolio.codewars.persistence

import androidx.paging.*
import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.persistence.remote.RemoteDataSource
import com.hfaria.portfolio.codewars.persistence.remote.api.AuthoredChallenges
import com.hfaria.portfolio.codewars.persistence.local.entity.CompletedChallengeEntity
import com.hfaria.portfolio.codewars.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CodeWarsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    fun getUser(username: String): DataWrapper<User> =
        remoteDataSource.getUserByUsername(username)

    fun getAuthoredChallenges(username: String): DataWrapper<AuthoredChallenges>
        = remoteDataSource.getAuthoredChallenges(username)

    fun getChallengeProfile(challengeId: String): DataWrapper<ChallengeProfile>
        = remoteDataSource.getChallengeProfile(challengeId)

    // TODO cleanup this
    fun getCompletedChallenges(username: String): Flow<PagingData<CompletedChallengeEntity>> {
       // val db = localDataSource.database
       // val keysDao = localDataSource.remoteKeysDao
       // val challengesDao = localDataSource.completedChallengeDao
       // val api = remoteDataSource.api
       // val sourceFactory = { challengesDao.getByUsername(username) }
       // return Pager(
       //     config = PagingConfig(
       //         pageSize = 200,
       //         enablePlaceholders = true
       //     ),
       //     pagingSourceFactory = sourceFactory,
       //     remoteMediator = CompletedChallengesMediator(username, db, keysDao, challengesDao, api)
       // ).flow
        return flow {}
    }

    //suspend fun getRecentUsers(): DataWrapper<List<User>>
    //        = localDataSource.getRecentUsers()
}