package com.hfaria.portfolio.codewars.persistence

import com.hfaria.portfolio.codewars.persistence.db.LocalDataSource
import com.hfaria.portfolio.codewars.persistence.network.RemoteDataSource
import com.hfaria.portfolio.codewars.persistence.network.api.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CodeWarsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    private val dataSource = DataSource(
        remoteDataSource::getUserByUsername,
        localDataSource::getUserByUsername,
        localDataSource::saveUser,
        localDataSource::hasUserCacheExpired
    )

    suspend fun getUser(username: String): Flow<DataWrapper<User>>
        = dataSource.query(username)

    suspend fun getRecentUsers(): Flow<Array<User>>
        = localDataSource.getRecentUsers()
}