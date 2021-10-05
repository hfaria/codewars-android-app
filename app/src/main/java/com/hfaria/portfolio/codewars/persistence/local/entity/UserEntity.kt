package com.hfaria.portfolio.codewars.persistence.remote.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hfaria.portfolio.codewars.util.TimeUtil

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    var username: String,
    var name: String,
    var searchTime: Int
) {
    companion object {
        fun fromDomain(user: User): UserEntity {
            if (user.name == null || user.name!!.isEmpty()) {
                user.name = "Unknown"
            }

            val timeNow = TimeUtil.nowInSeconds()
            val userEntity = UserEntity(user.username, user.name!!, timeNow)
            return userEntity
        }

        fun toDomain(entity: UserEntity): User {
            return User(entity.username, entity.name, entity.searchTime)
        }
    }
}