package com.hfaria.portfolio.codewars.persistence

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hfaria.portfolio.codewars.persistence.network.RemoteDataSource
import com.hfaria.portfolio.codewars.persistence.network.api.CompletedChallenge
import retrofit2.HttpException
import java.io.IOException

class PaginatedDataSource (
    private val username: String,
    private val remoteDataSource: RemoteDataSource,
) : PagingSource<Int, CompletedChallenge>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompletedChallenge> {
        val position = params.key ?: 0
        return try {
            val response = remoteDataSource.getCompletedChallenges(username, position)
            val page = response.data!!.data
            LoadResult.Page(
                data = page,
                prevKey = if (position == 0) null else position,
                nextKey = if (page.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CompletedChallenge>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.anchorPosition
        }
    }
}