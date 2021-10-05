package com.hfaria.portfolio.codewars.persistence

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hfaria.portfolio.codewars.persistence.db.LocalDataSource
import com.hfaria.portfolio.codewars.persistence.network.RemoteDataSource
import com.hfaria.portfolio.codewars.persistence.network.api.AuthoredChallenges
import com.hfaria.portfolio.codewars.persistence.network.api.CompletedChallenge
import com.hfaria.portfolio.codewars.persistence.network.api.User
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

    suspend fun getRecentUsers(): Flow<Array<User>>
            = localDataSource.getRecentUsers()

    suspend fun getUser(username: String): Flow<DataWrapper<User>>
        = userDataSource.query(username)

    suspend fun getAuthoredChallenges(username: String): Flow<DataWrapper<AuthoredChallenges>> = flow {
        val challenges = remoteDataSource.getAuthoredChallenges(username)
        emit(challenges)
    }

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