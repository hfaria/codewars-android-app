package com.hfaria.portfolio.codewars.test_setup

import com.hfaria.portfolio.codewars.persistence.local.dao.UserDao
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity

class StubUserDao : UserDao {
    override fun getAll(): List<UserEntity> {
        TODO("Not yet implemented")
    }

    override fun getByUsername(username: String): UserEntity? {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun deleteAllButLast(limit: Int) {
        TODO("Not yet implemented")
    }

    override fun insert(obj: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun insert(vararg obj: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun insertSoft(obj: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun insertSoft(vararg obj: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun update(obj: UserEntity) {
        TODO("Not yet implemented")
    }

    override fun delete(obj: UserEntity) {
        TODO("Not yet implemented")
    }
}