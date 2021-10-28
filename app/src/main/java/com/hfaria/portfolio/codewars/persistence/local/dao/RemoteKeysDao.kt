package com.hfaria.portfolio.codewars.persistence.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hfaria.portfolio.codewars.persistence.local.entity.RemoteKeysEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    fun getById(id: String): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    fun deleteAll()
}

