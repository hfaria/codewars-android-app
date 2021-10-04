package com.hfaria.portfolio.codewars.persistence

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hfaria.portfolio.codewars.persistence.db.LocalDataSource
import com.hfaria.portfolio.codewars.persistence.network.RemoteDataSource
import com.hfaria.portfolio.codewars.persistence.network.api.CompletedChallenge
import com.hfaria.portfolio.codewars.persistence.network.api.User
import kotlinx.coroutines.flow.Flow
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

    suspend fun getUser(username: String): Flow<DataWrapper<User>>
        = userDataSource.query(username)

    suspend fun getRecentUsers(): Flow<Array<User>>
        = localDataSource.getRecentUsers()

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