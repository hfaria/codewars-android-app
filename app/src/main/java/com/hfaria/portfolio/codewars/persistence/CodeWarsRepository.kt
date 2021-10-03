package com.hfaria.portfolio.codewars.persistence

import com.hfaria.portfolio.codewars.persistence.db.LocalDataSource
import com.hfaria.portfolio.codewars.persistence.network.RemoteDataSource
import com.hfaria.portfolio.codewars.persistence.network.api.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CodeWarsRepository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {

    private val dataSource = DataSource(
        remoteDataSource::getUserByUsername,
        localDataSource::getUserByUsername,
        localDataSource::saveUser
    )

    suspend fun getUser(username: String): Flow<DataWrapper<User>>
        = dataSource.query(username)
}