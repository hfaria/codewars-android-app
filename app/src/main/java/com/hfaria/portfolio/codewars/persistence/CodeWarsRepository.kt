package com.hfaria.portfolio.codewars.persistence

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.persistence.local.LocalDataSource
import com.hfaria.portfolio.codewars.persistence.remote.RemoteDataSource
import com.hfaria.portfolio.codewars.persistence.remote.api.AuthoredChallenges
import com.hfaria.portfolio.codewars.persistence.remote.api.CompletedChallenge
import com.hfaria.portfolio.codewars.persistence.remote.api.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CodeWarsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    private val userDataSource = DataSource(
        remoteDataSource::getUserByUsername,
        localDataSource::getUserByUsername,
        localDataSource::saveUser,
        localDataSource::hasUserCacheExpired
    )

    private val authoredChallengesSource = DataSource(
        remoteDataSource::getAuthoredChallenges,
        localDataSource::getAuthoredChallenges,
        localDataSource::saveAuthoredChallenges,
        localDataSource::hasAuthoredChallengesExpired
    )

    private val challengeProfileSource = DataSource(
        remoteDataSource::getChallengeProfile,
        localDataSource::getChallengeProfileById,
        localDataSource::saveChallengeProfile,
        localDataSource::hasChallengeProfileCacheExpired
    )

    suspend fun getRecentUsers(): Flow<List<User>>
            = localDataSource.getRecentUsers()

    suspend fun getUser(username: String): Flow<DataWrapper<User>>
        = userDataSource.query(username)

    suspend fun getAuthoredChallenges(username: String): Flow<DataWrapper<AuthoredChallenges>>
        = authoredChallengesSource.query(username)

    suspend fun getChallengeProfile(challengeId: String): Flow<DataWrapper<ChallengeProfile>>
        = challengeProfileSource.query(challengeId)

    suspend fun getCompletedChallenges(username: String): Flow<PagingData<CompletedChallenge>> {
        return Pager(
            config = PagingConfig(
                pageSize = 200,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { PaginatedDataSource(username, remoteDataSource) }
        ).flow
    }
}