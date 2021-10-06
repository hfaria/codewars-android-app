package com.hfaria.portfolio.codewars.persistence.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfaria.portfolio.codewars.persistence.local.dao.*
import com.hfaria.portfolio.codewars.persistence.local.entity.AuthoredChallengeEntity
import com.hfaria.portfolio.codewars.persistence.remote.api.ChallengeProfileEntity
import com.hfaria.portfolio.codewars.persistence.local.entity.CompletedChallengeEntity
import com.hfaria.portfolio.codewars.persistence.local.entity.RemoteKeysEntity
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "user-database"

@Database(
    entities = [
        UserEntity::class,
        CompletedChallengeEntity::class,
        RemoteKeysEntity::class,
        AuthoredChallengeEntity::class,
        ChallengeProfileEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun authoredChallengeDao(): AuthoredChallengeDao
    abstract fun challengeProfileDao(): ChallengeProfileDao
    abstract fun completedChallengeDao(): CompletedChallengeDao
    abstract fun remoteKeyDao(): RemoteKeysDao
}
