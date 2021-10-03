package com.hfaria.portfolio.codewars.persistence

import androidx.lifecycle.*
import com.hfaria.portfolio.codewars.persistence.network.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.network.api.User
import com.hfaria.portfolio.codewars.persistence.network.reactive.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CodeWarsRepository @Inject constructor() {

    val client = Retrofit.Builder()
        .baseUrl("https://www.codewars.com/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()

    val api = client.create(CodeWarsApi::class.java)

    fun getUser(username: String): LiveData<Resource<User>> {
        val call = api.getUsers(username)
        val result = Transformations.map(call) { apiResponse ->
            when (apiResponse) {
                is ApiLoadingResponse -> Resource.loading(null)
                is ApiSuccessResponse -> Resource.success(apiResponse.body)
                is ApiEmptyResponse -> Resource.error("EMPTYRES", null)
                is ApiErrorResponse -> Resource.error(apiResponse.errorMessage, null)
            }
        }

        return result
    }
}