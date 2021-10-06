package com.hfaria.portfolio.codewars.persistence.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hfaria.portfolio.codewars.persistence.local.entity.AuthoredChallengeEntity

@Dao
interface AuthoredChallengeDao: BaseDao<AuthoredChallengeEntity> {

    @Insert
    suspend fun insertAll(challenges: List<AuthoredChallengeEntity>)

    @Query("DELETE from authored_challenge")
    suspend fun deleteAll()

    @Query("DELETE from authored_challenge WHERE author = :author")
    suspend fun deleteAllFromAuthor(author: String)
}