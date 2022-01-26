package com.hfaria.portfolio.codewars.integration

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.integration.setup.DaggerNetworkTestComponent
import com.hfaria.portfolio.codewars.integration.setup.NetworkTestComponent
import com.hfaria.portfolio.codewars.persistence.remote.adapter.ApiNotFoundResponse
import com.hfaria.portfolio.codewars.persistence.remote.adapter.ApiSuccessResponse
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.acceptance.setup.TestCodeWarsApp
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class UserNetworkTest {
    @Before
    fun setupDI() {
        val application = TestCodeWarsApp()
        val component = DaggerNetworkTestComponent
            .builder()
            .application(application)
            .build()
        injectTest(component)
    }

    fun injectTest(component: NetworkTestComponent) = component.inject(this)

    @Inject
    lateinit var api: CodeWarsApi

    @Test
    fun test_get_existing_user() {
        val username = "g964"
        val response = api.getUsers(username) as ApiSuccessResponse<User>
        assertEquals(username, response.body.username)
    }

    @Test
    fun test_get_unexisting_user() {
        val username = "unexisting_user"
        val response = api.getUsers(username) as ApiNotFoundResponse<User>
    }
}