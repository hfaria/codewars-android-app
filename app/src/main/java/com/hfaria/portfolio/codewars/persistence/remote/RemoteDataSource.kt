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

    suspend fun getUserByUsername(username: String) : DataWrapper<User> {
        var userData: DataWrapper<User>

        withContext(Dispatchers.IO) {
            userData = api.getUsers(username)
        }

        return userData
    }

    suspend fun getCompletedChallenges(username: String, page: Int) : DataWrapper<CompletedChallengesPage> {
        var data: DataWrapper<CompletedChallengesPage>

        withContext(Dispatchers.IO) {
            data = api.getCompletedChallenges(username, page)
        }

        return data
    }

    suspend fun getAuthoredChallenges(username: String) : DataWrapper<AuthoredChallenges> {
        var wrapper : DataWrapper<AuthoredChallenges>

        withContext(Dispatchers.IO) {
            wrapper = api.getAuthoredChallenges(username)
            wrapper.data?.author = username
        }

        return wrapper
    }

    suspend fun getChallengeProfile(challengeId: String) : DataWrapper<ChallengeProfile> {
        var wrapper : DataWrapper<ChallengeProfile>

        withContext(Dispatchers.IO) {
            wrapper = api.getChallengeProfile(challengeId)
        }

        return wrapper
    }
}