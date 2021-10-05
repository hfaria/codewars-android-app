package com.hfaria.portfolio.codewars

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hfaria.portfolio.codewars.persistence.local.db.AppDatabase
import com.hfaria.portfolio.codewars.persistence.remote.api.User
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CodeWarsDatabaseTest {

    // TODO add dependency injection
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val db = Room
            .inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .fallbackToDestructiveMigration()
            .build()
    val userDao = db.userDao()

    @Test
    fun testInsertUser() = runBlocking {
        //val user = User(username = "g964", name= "", insertionTime = 0)
        //userDao.insert(user)
        //val users = userDao.getAll()

        //assertEquals(users.size, 1)
        //assertEquals(users[0].username, "g964")
    }
}