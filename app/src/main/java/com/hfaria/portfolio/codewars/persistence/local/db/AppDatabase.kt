package com.hfaria.portfolio.codewars.persistence.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfaria.portfolio.codewars.persistence.local.dao.UserDao
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "user-database"

@Database(
    entities = [UserEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
