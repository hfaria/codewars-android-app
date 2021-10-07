package com.hfaria.portfolio.codewars.persistence.remote

import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.remote.api.AuthoredChallenges
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.remote.api.CompletedChallengesPage
import com.hfaria.portfolio.codewars.persistence.remote.api.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    val api: CodeWarsApi
) {

    protected suspend fun <T> runOnIOThread(call: suspend () -> DataWrapper<T>)
        : DataWrapper<T> {
        return withContext(Dispatchers.IO) {
            call.runCatching { invoke() }
                .onFailure { t ->  DataWrapper.exception(t, null)}
                .getOrThrow()
        }
    }

    suspend fun getUserByUsername(username: String): DataWrapper<User>
        = runOnIOThread { api.getUsers(username) }

    suspend fun getCompletedChallenges(username: String, page: Int): DataWrapper<CompletedChallengesPage>
        = runOnIOThread { api.getCompletedChallenges(username, page) }

    suspend fun getAuthoredChallenges(username: String): DataWrapper<AuthoredChallenges>
        = runOnIOThread {
            val wrapper = api.getAuthoredChallenges(username)
            wrapper.data?.author = username
            wrapper
        }

    suspend fun getChallengeProfile(challengeId: String): DataWrapper<ChallengeProfile>
        = runOnIOThread{ api.getChallengeProfile(challengeId) }
}