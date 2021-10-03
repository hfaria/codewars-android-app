package com.hfaria.portfolio.codewars.persistence.network.api

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val CODEWARS_ENDPOINT = "https://www.codewars.com/api/v1/"

interface CodeWarsApi {

    @Headers("Content-Type: application/json")
    @GET("users/{uname}")
    fun getUsers(@Path("uname") username: String): DataWrapper<User>
}