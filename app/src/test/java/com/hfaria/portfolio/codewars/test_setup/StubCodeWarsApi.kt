package com.hfaria.portfolio.codewars.test_setup

import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.remote.api.AuthoredChallenges
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.remote.api.CompletedChallengesPage
import com.hfaria.portfolio.codewars.domain.User

class StubCodeWarsApi: CodeWarsApi {

    lateinit var getUserReponse: DataWrapper<User>

    override fun getUsers(username: String): DataWrapper<User> {
        return getUserReponse
    }

    override fun getCompletedChallenges(
        username: String,
        page: Int
    ): DataWrapper<CompletedChallengesPage> {
        TODO("Not yet implemented")
    }

    override fun getAuthoredChallenges(username: String): DataWrapper<AuthoredChallenges> {
        TODO("Not yet implemented")
    }

    override fun getChallengeProfile(challengeId: String): DataWrapper<ChallengeProfile> {
        TODO("Not yet implemented")
    }
}