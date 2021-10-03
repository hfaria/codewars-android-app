package com.hfaria.portfolio.codewars.persistence.db

import androidx.room.Dao
import androidx.room.Query
import com.hfaria.portfolio.codewars.persistence.network.api.User

@Dao
interface UserDao: BaseDao<User> {
    @Query("SELECT * from user")
    suspend fun getAll(): Array<User>

    @Query("SELECT * from user WHERE :username = username")
    suspend fun getByUsername(username: String): User?

    @Query("DELETE from user")
    suspend fun deleteAll()
}