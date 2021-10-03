package com.hfaria.portfolio.codewars.persistence.network

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.network.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.network.api.User
import com.hfaria.portfolio.codewars.persistence.network.reactive.ApiResponse
import com.hfaria.portfolio.codewars.persistence.network.reactive.DataWrapperCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: CodeWarsApi
) {

    suspend fun getUserByUsername(username: String) : DataWrapper<User> {
        var userData: DataWrapper<User>

        withContext(Dispatchers.IO) {
            userData = api.getUsers(username)
        }

        return userData
    }
}