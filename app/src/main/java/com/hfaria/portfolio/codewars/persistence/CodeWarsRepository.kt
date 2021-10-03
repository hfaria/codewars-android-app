package com.hfaria.portfolio.codewars.persistence

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.persistence.network.RemoteDataSource
import com.hfaria.portfolio.codewars.persistence.network.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.network.api.User
import com.hfaria.portfolio.codewars.persistence.network.reactive.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CodeWarsRepository @Inject constructor() {

    val remoteDataSource = RemoteDataSource()
    val dataSource = DataSource(
        remoteDataSource::fetchUsers
    )

    suspend fun getUser(username: String): Flow<DataWrapper<User>?>
        = dataSource.query(username)
}