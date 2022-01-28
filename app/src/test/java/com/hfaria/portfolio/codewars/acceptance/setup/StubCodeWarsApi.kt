package com.hfaria.portfolio.codewars.acceptance.setup

import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.remote.RemoteResponse

class StubCodeWarsApi: CodeWarsApi {

    lateinit var getUserReponse: RemoteResponse<User>
    var getUserException: Exception? = null

    override fun getUsers(username: String): RemoteResponse<User> {
        if (getUserException == null) {
            return getUserReponse
        } else {
            throw getUserException!!
        }
    }

}