package com.hfaria.portfolio.codewars.persistence.remote.api

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.Mapper
import javax.inject.Inject

const val UNKNOWN_NAME = "Unknown"

class UserMapper
    @Inject constructor() : Mapper<UserNetwork, User> {
    override fun map(obj: UserNetwork): User {
        return User(obj.username, obj.name ?: UNKNOWN_NAME, 0)
    }
}