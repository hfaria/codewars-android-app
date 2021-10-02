package com.hfaria.portfolio.codewars.persistence

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.persistence.network.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.network.api.User
import com.hfaria.portfolio.codewars.persistence.network.reactive.ApiEmptyResponse
import com.hfaria.portfolio.codewars.persistence.network.reactive.ApiErrorResponse
import com.hfaria.portfolio.codewars.persistence.network.reactive.ApiSuccessResponse
import com.hfaria.portfolio.codewars.persistence.network.reactive.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryImpl: Repository {

    val client = Retrofit.Builder()
        .baseUrl("https://www.codewars.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()

    val api = client.create(CodeWarsApi::class.java)

    override fun getUser(username: String): LiveData<Resource<User>> {
        val call = api.getUsers(username)
        val result = Transformations.map(call) { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> Resource.success(apiResponse.body)
                is ApiEmptyResponse -> Resource.error("EMPTYRES", null)
                is ApiErrorResponse -> Resource.error(apiResponse.errorMessage, null)
            }
        }

        return result
    }
}