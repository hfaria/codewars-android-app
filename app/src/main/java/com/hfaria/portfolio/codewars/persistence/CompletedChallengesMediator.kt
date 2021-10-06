package com.hfaria.portfolio.codewars.persistence

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.hfaria.portfolio.codewars.persistence.local.dao.CompletedChallengeDao
import com.hfaria.portfolio.codewars.persistence.local.dao.RemoteKeysDao
import com.hfaria.portfolio.codewars.persistence.local.db.AppDatabase
import com.hfaria.portfolio.codewars.persistence.local.entity.CompletedChallengeEntity
import com.hfaria.portfolio.codewars.persistence.local.entity.RemoteKeysEntity
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.remote.api.CompletedChallengesPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class CompletedChallengesMediator (
    private val username: String,
    private val database: AppDatabase,
    private val keysDao: RemoteKeysDao,
    private val challengeDao: CompletedChallengeDao,
    private val codeWarsApi: CodeWarsApi,
) : RemoteMediator<Int, CompletedChallengeEntity>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, CompletedChallengeEntity>
    ): MediatorResult {

        val pageKeyData: Int? = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.APPEND -> {
                val remoteKeys = database.withTransaction {
                    getLastRemoteKey(state)
                }

                if (remoteKeys == null) {
                    0
                } else {
                    if (remoteKeys.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKeys.nextKey
                }
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        val page = pageKeyData ?: 0

        try {
            var isEndOfList: Boolean
            var result: MediatorResult
            result = MediatorResult.Success(true)

            var response: DataWrapper<CompletedChallengesPage>

            withContext(Dispatchers.IO) {
                response = codeWarsApi.getCompletedChallenges(username, page)
            }

            var challenges: List<CompletedChallengeEntity> = emptyList()

            if (response.hasData()) {
                challenges = response.data!!.data
            } else {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            database.withTransaction {
                isEndOfList = challenges.isEmpty()
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    keysDao.deleteAll()
                    challengeDao.deleteAll()
                }
                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = challenges.map {
                    it.author = username

                    if (it.name == null || it.name.isEmpty()) {
                        it.name = "Unknown"
                    }

                    if (it.slug == null || it.slug.isEmpty()) {
                        it.slug= "Unknown"
                    }

                    RemoteKeysEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                keysDao.insertAll(keys)
                challengeDao.insertAll(challenges)
                result = MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }

            return result
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, CompletedChallengeEntity>): RemoteKeysEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { challenge -> keysDao.getById(challenge.id) }
    }

}
