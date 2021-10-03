package com.hfaria.portfolio.codewars.persistence.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfaria.portfolio.codewars.persistence.network.api.User

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "user-database"

@Database(
    entities = [User::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
