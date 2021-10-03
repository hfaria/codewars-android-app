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

class RemoteDataSource {

    val client = Retrofit.Builder()
        .baseUrl("https://www.codewars.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(DataWrapperCallAdapterFactory())
        .build()

    val api = client.create(CodeWarsApi::class.java)

    suspend fun fetchUsers(username: String) : DataWrapper<User> {
        var userData: DataWrapper<User>

        withContext(Dispatchers.IO) {
            userData = api.getUsers(username)
        }

        return userData
    }
}