package com.hfaria.portfolio.codewars

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hfaria.portfolio.codewars.persistence.local.db.AppDatabase
import com.hfaria.portfolio.codewars.persistence.remote.api.UserEntity
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

// TODO add dependency injection
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val db = Room
        .inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
        .fallbackToDestructiveMigration()
        .build()
    val userDao = db.userDao()
    //val authoredChallengeDao = db.authoredChallengeDao()
    //val localDs = LocalDataSource(userDao, authoredChallengeDao)

    @Test
    fun testInsertUser() = runBlocking {
        val user = UserEntity(username = "g964", name = "", updatedAt = 0)
        userDao.insert(user)
        val users = userDao.getAll()
        assertEquals(users.size, 1)
        assertEquals(users[0].username, "g964")
    }

    //@Test
    //fun testInsertUserWithAuthoredChallenges() = runBlocking {
    //    val user = User(username = "g964", name = "", updatedAt = 0)
    //    localDs.saveUser(user)
    //    val chal1 = AuthoredChallengeEntity(id = "1", name = "chal1", author = "g964")
    //    val chal2 = AuthoredChallengeEntity(id = "2", name = "chal2", author = "g964")
    //    val challenges = listOf(chal1, chal2)
    //    authoredChallengeDao.insertAll(challenges)

    //    val user2 = User(username = "tourist", name = "", updatedAt = 0)
    //    localDs.saveUser(user2)
    //    val chal3 = AuthoredChallengeEntity(id = "3", name = "chal3", author = "tourist")
    //    val chal4 = AuthoredChallengeEntity(id = "4", name = "chal4", author = "tourist")
    //    val challenges2 = listOf(chal3, chal4)
    //    authoredChallengeDao.insertAll(challenges2)

    //    val res1 = userDao.getAuthoredChallenges(user.username)
    //    assertEquals(2, res1!!.authoredChallenges.size)
    //    assertEquals(res1.authoredChallenges[0].author, "g964")
    //}

    //@Test
    //fun testDeleteUserShouldDeleteAuthoredChallenges() = runBlocking {
    //    var user = User(username = "g964", name = "", updatedAt = 0)
    //    localDs.saveUser(user)
    //    val chal1 = AuthoredChallengeEntity(id = "1", name = "chal1", author = "g964")
    //    val chal2 = AuthoredChallengeEntity(id = "2", name = "chal2", author = "g964")
    //    val challenges = listOf(chal1, chal2)
    //    authoredChallengeDao.insertAll(challenges)
    //    val userEntity = userDao.getByUsername(user.username)
    //    userDao.delete(userEntity!!)

    //    val res1 = userDao.getAuthoredChallenges(user.username)
    //    assertNull(res1)
    //}

    //@Test
    //fun testUpdateUserShouldNotRemoveAuthoredChallenges() = runBlocking {
    //    var user = User(username = "g964", name = "", updatedAt = 0)
    //    localDs.saveUser(user)
    //    val chal1 = AuthoredChallengeEntity(id = "1", name = "chal1", author = "g964")
    //    val chal2 = AuthoredChallengeEntity(id = "2", name = "chal2", author = "g964")
    //    val challenges = listOf(chal1, chal2)
    //    authoredChallengeDao.insertAll(challenges)
    //    val userEntity = userDao.getByUsername(user.username)
    //    userEntity!!.name = "TheBest"
    //    userDao.update(userEntity)

    //    val res1 = userDao.getAuthoredChallenges(user.username)
    //    assertEquals(2, res1!!.authoredChallenges.size)
    //    assertEquals(res1.authoredChallenges[0].author, "g964")
    //}

    //@Test
    //fun testRemoveAuthoredChallenges() = runBlocking {
    //    var user = User(username = "g964", name = "", updatedAt = 0)
    //    localDs.saveUser(user)
    //    val chal1 = AuthoredChallengeEntity(id = "1", name = "chal1", author = "g964")
    //    val chal2 = AuthoredChallengeEntity(id = "2", name = "chal2", author = "g964")
    //    val challenges = listOf(chal1, chal2)
    //    authoredChallengeDao.insertAll(challenges)
    //    authoredChallengeDao.deleteAll()

    //    val res1 = userDao.getAuthoredChallenges(user.username)
    //    assertTrue(res1!!.authoredChallenges.isEmpty())
    //}
}