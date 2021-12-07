package com.hfaria.portfolio.codewars.persistence.remote.api

import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val CODEWARS_ENDPOINT = "https://www.codewars.com/api/v1/"

interface CodeWarsApi {

    @GET("users/{uname}")
    fun getUsers(@Path("uname") username: String): DataWrapper<User>

    @GET("users/{uname}/code-challenges/completed?")
    fun getCompletedChallenges(@Path("uname") username: String, @Query("page") page: Int)
    : DataWrapper<CompletedChallengesPage>

    @GET("users/{uname}/code-challenges/authored")
    fun getAuthoredChallenges(@Path("uname") username: String)
            : DataWrapper<AuthoredChallenges>

    @GET("code-challenges/{challenge}")
    fun getChallengeProfile(@Path("challenge") challengeId: String)
            : DataWrapper<ChallengeProfile>
}