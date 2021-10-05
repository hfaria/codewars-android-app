package com.hfaria.portfolio.codewars.persistence.remote.api

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val CODEWARS_ENDPOINT = "https://www.codewars.com/api/v1/"

interface CodeWarsApi {

    @Headers("Content-Type: application/json")
    @GET("users/{uname}")
    fun getUsers(@Path("uname") username: String): DataWrapper<User>

    @Headers("Content-Type: application/json")
    @GET("users/{uname}/code-challenges/completed?")
    fun getCompletedChallenges(@Path("uname") username: String, @Query("page") page: Int)
    : DataWrapper<CompletedChallengesPage>

    @Headers("Content-Type: application/json")
    @GET("users/{uname}/code-challenges/authored")
    fun getAuthoredChallenges(@Path("uname") username: String)
            : DataWrapper<AuthoredChallenges>
}