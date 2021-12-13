package com.hfaria.portfolio.codewars.test_setup

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.domain.User

class StubCodeWarsApi: CodeWarsApi {

    lateinit var getUserReponse: DataWrapper<User>
    var getUserException: Exception? = null

    override fun getUsers(username: String): DataWrapper<User> {
        if (getUserException == null) {
            return getUserReponse
        } else {
            throw getUserException!!
        }
    }

}