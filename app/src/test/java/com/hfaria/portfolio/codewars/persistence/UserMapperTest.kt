package com.hfaria.portfolio.codewars.persistence

import com.hfaria.portfolio.codewars.persistence.remote.api.UNKNOWN_NAME
import com.hfaria.portfolio.codewars.persistence.remote.api.UserMapper
import com.hfaria.portfolio.codewars.persistence.remote.api.UserNetwork
import junit.framework.Assert.assertEquals
import org.junit.Test

class UserMapperTest {

    private val mapper = UserMapper()

    @Test
    fun map_everything() {
        val networkUser = UserNetwork("uname", "name")
        val user = mapper.map(networkUser)
        assertEquals(user.username, networkUser.username)
    }

    @Test
    fun map_with_unknown_name() {
        val networkUser = UserNetwork("uname", null)
        val user = mapper.map(networkUser)
        assertEquals(user.name, UNKNOWN_NAME)
    }
}