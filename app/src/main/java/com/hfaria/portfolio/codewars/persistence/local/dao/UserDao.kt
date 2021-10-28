package com.hfaria.portfolio.codewars.persistence.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.hfaria.portfolio.codewars.persistence.local.entity.UserWithAuthoredChallenges
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity

@Dao
interface UserDao: BaseDao<UserEntity> {
    @Query("SELECT * from user")
    fun getAll(): List<UserEntity>

    @Query("SELECT * from user WHERE :username = username")
    fun getByUsername(username: String): UserEntity?

    @Query("DELETE from user")
    fun deleteAll()

    @Query("DELETE from user WHERE username NOT IN (SELECT username FROM user ORDER BY updatedAt DESC LIMIT :limit)")
    fun deleteAllButLast(limit: Int)

    @Transaction
    @Query("SELECT * FROM user WHERE username = :username")
    fun getAuthoredChallenges(username: String): UserWithAuthoredChallenges?
}