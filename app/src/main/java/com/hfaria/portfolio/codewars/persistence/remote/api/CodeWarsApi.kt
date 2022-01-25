package com.hfaria.portfolio.codewars.persistence.remote.api

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.remote.adapter.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

const val CODEWARS_ENDPOINT = "https://www.codewars.com/api/v1/"

interface CodeWarsApi {

    @GET("users/{uname}")
    fun getUsers(@Path("uname") username: String): ApiResponse<User>

}