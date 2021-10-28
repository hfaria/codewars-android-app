package com.hfaria.portfolio.codewars.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataSource<Input, Output>(
    private val remoteFetch: (Input) -> DataWrapper<Output>,
    private val localFetch: (Input) -> DataWrapper<Output>,
    private val localStore: (Output) -> Unit,
    private val hasExpired: (Output) -> Boolean,
) {

    /*
        Generic algorithm to provide cache policy and offline functionality
     */
    fun query(args: Input, force: Boolean = false): Flow<DataWrapper<Output>> = flow {
        val localCache = localFetch(args)

        /* Unless explicit forced update,
            emit cached result, if any */
        if (!force && localCache.hasData()) {
            emit(localCache)
        }

        val cacheExpired = if (localCache.hasData()) {
            hasExpired(localCache.data!!)
        } else {
            true
        }

        /* If cache expired or forced update,
            look for fresh data */
        if (cacheExpired || force) {
            val remoteWrapper = remoteFetch(args)

            if (!remoteWrapper.hasData()) {
                /* Fresh data request failed for some reason.
                   If we have cached data, emit it.
                   Else, just report failure */
                if(localCache.hasData()) {
                    emit(localCache)
                } else {
                    emit(remoteWrapper)
                }
            } else {
                /* Fresh data was successfully obtained.
                   Update cache
                   and emit fresh data */
                localStore(remoteWrapper.data!!)
                emit(remoteWrapper)
            }
        }
    }.flowOn(Dispatchers.IO)
}