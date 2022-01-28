package com.hfaria.portfolio.codewars.acceptance.setup

import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.remote.ApiResponse

class StubCodeWarsApi: CodeWarsApi {

    lateinit var getUserReponse: ApiResponse<User>
    var getUserException: Exception? = null

    override fun getUsers(username: String): ApiResponse<User> {
        if (getUserException == null) {
            return getUserReponse
        } else {
            throw getUserException!!
        }
    }

}