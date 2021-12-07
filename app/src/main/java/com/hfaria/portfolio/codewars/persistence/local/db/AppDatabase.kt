package com.hfaria.portfolio.codewars.persistence.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hfaria.portfolio.codewars.persistence.local.dao.*
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "user-database"

@Database(
    entities = [
        UserEntity::class,
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(DbTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
