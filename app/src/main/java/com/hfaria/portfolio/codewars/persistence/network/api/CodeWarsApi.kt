package com.hfaria.portfolio.codewars.persistence.network.api

import androidx.lifecycle.LiveData
import com.hfaria.portfolio.codewars.persistence.network.reactive.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CodeWarsApi {

    @Headers("Content-Type: application/json")
    @GET("users/{uname}")
    fun getUsers(@Path("uname") username: String): LiveData<ApiResponse<User>>
}