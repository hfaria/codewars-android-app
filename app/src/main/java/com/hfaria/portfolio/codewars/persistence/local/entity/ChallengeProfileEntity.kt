package com.hfaria.portfolio.codewars.persistence.remote.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hfaria.portfolio.codewars.domain.ChallengeProfile
import com.hfaria.portfolio.codewars.util.TimeUtil

@Entity(tableName = "challenge_profile")
data class ChallengeProfileEntity(
    @PrimaryKey
    var id: String,
    var name: String,
    var description: String,
    var updatedAt: Int,
) {
    companion object {
        fun fromDomain(ch: ChallengeProfile): ChallengeProfileEntity {
            val timeNow = TimeUtil.nowInSeconds()
            val userEntity = ChallengeProfileEntity(ch.id, ch.name, ch.description, timeNow)
            return userEntity
        }

        fun toDomain(entity: ChallengeProfileEntity): ChallengeProfile {
            return ChallengeProfile(entity.id, entity.name, entity.description, entity.updatedAt)
        }
    }
}