package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.remote.api.AuthoredChallenges
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.remote.api.CompletedChallengesPage
import com.hfaria.portfolio.codewars.domain.User
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val api: CodeWarsApi
) {

    private fun <T> run(call: () -> DataWrapper<T>) =
        call.runCatching { invoke() }
            .onFailure { t ->  DataWrapper.exception(t, null)}
            .getOrThrow()

    fun getUserByUsername(username: String): DataWrapper<User>
        = run { api.getUsers(username) }

    fun getAuthoredChallenges(username: String): DataWrapper<AuthoredChallenges>
        = run {
            val wrapper = api.getAuthoredChallenges(username)
            wrapper.data?.author = username
            wrapper
        }

    fun getChallengeProfile(challengeId: String): DataWrapper<ChallengeProfile>
        = run { api.getChallengeProfile(challengeId) }

    fun getCompletedChallenges(username: String, page: Int): DataWrapper<CompletedChallengesPage>
            = run { api.getCompletedChallenges(username, page) }
}