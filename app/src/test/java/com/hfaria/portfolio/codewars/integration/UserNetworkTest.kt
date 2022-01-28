package com.hfaria.portfolio.codewars.integration

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.integration.setup.NetworkTestComponent
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.integration.setup.BaseNetworkTest
import com.hfaria.portfolio.codewars.persistence.remote.RemoteNotFoundResponse
import com.hfaria.portfolio.codewars.persistence.remote.RemoteSuccessResponse
import junit.framework.Assert.*
import org.junit.Test
import javax.inject.Inject

class UserNetworkTest: BaseNetworkTest() {

    override fun injectTest(component: NetworkTestComponent)
        = component.inject(this)

    @Inject
    lateinit var api: CodeWarsApi

    @Test
    fun test_get_existing_user() {
        val username = "g964"
        val response = api.getUsers(username) as RemoteSuccessResponse<User>
        assertEquals(username, response.body.username)
    }

    @Test
    fun test_get_unexisting_user() {
        val username = "unexisting_user"
        val response = api.getUsers(username) as RemoteNotFoundResponse<User>
    }
}