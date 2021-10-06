package com.hfaria.portfolio.codewars.persistence.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity

data class UserWithAuthoredChallenges(
    @Embedded
    val user: UserEntity,

    @Relation(
        parentColumn = "username",
        entityColumn = "author"
    )
    val authoredChallenges: List<AuthoredChallengeEntity>
)