package com.hfaria.portfolio.codewars.persistence.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfaria.portfolio.codewars.persistence.local.entity.CompletedChallengeEntity

@Dao
interface CompletedChallengeDao: BaseDao<CompletedChallengeEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(challenges: List<CompletedChallengeEntity>)

    @Query("SELECT * from completed_challenge WHERE author == :username")
    fun getByUsername(username: String): PagingSource<Int, CompletedChallengeEntity>

    @Query("DELETE from completed_challenge")
    fun deleteAll()

}