package com.hfaria.portfolio.codewars.persistence.remote.api

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hfaria.portfolio.codewars.util.TimeUtil

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    var username: String,
    var name: String,
    var updatedAt: Int,
    var updatedAuthoredChallengesAt: Int,
    @Embedded
    var overallRank: Rank,
    var languageRanks: List<LanguageRank>
) {
    companion object {
        fun fromDomain(user: User): UserEntity {
            if (user.name == null || user.name!!.isEmpty()) {
                user.name = "Unknown"
            }

            val timeNow = TimeUtil.nowInSeconds()
            val userEntity = UserEntity(
                user.username, user.name!!,
                timeNow, 0,
                user.ranks!!.overall,
                user.ranks!!.languages
            )
            return userEntity
        }

        fun toDomain(entity: UserEntity): User {
            val userRanks = UserRanks(entity.overallRank, entity.languageRanks)
            return User(entity.username, entity.name, entity.updatedAt, userRanks, entity.updatedAuthoredChallengesAt)
        }
    }
}