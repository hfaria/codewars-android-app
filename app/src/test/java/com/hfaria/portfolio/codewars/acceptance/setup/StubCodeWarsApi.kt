package com.hfaria.portfolio.codewars.acceptance.setup

import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.remote.RemoteResponse
import com.hfaria.portfolio.codewars.persistence.remote.api.UserNetwork

class StubCodeWarsApi: CodeWarsApi {

    lateinit var getUserReponse: RemoteResponse<UserNetwork>
    var getUserException: Exception? = null

    override fun getUsers(username: String): RemoteResponse<UserNetwork> {
        if (getUserException == null) {
            return getUserReponse
        } else {
            throw getUserException!!
        }
    }

}