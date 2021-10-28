package com.hfaria.portfolio.codewars.persistence.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.hfaria.portfolio.codewars.persistence.remote.api.ChallengeProfileEntity

@Dao
interface ChallengeProfileDao: BaseDao<ChallengeProfileEntity> {

    @Query("SELECT * from challenge_profile WHERE :id = id")
    fun getById(id: String): ChallengeProfileEntity?
}