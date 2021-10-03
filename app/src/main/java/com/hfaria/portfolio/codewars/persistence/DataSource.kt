package com.hfaria.portfolio.codewars.persistence

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DataSource<in Input, out Output>(
    private val remoteFetch: suspend (Input) -> Output,
    private val localFetch: suspend (Input) -> Output,
    private val localStore: suspend (Output) -> Unit,
    //private val localDelete: suspend () -> Unit,
) {

    //init {
    //    refreshControl.addListener(this)
    //}

    // Public API
    suspend fun query(args: Input, force: Boolean = false): Flow<Output> = flow {
        //if (!force) {
        //    fetchFromLocal(args).run { emit(this) }
        //}
        //if (refreshControl.isExpired() || force) {
        if(true) {
            fetchFromRemote(args).run { emit(this) }
        }
    }

    //override suspend fun cleanup() {
    //    deleteLocal()
    //}

    // Private API
    //private suspend fun deleteLocal() = kotlin.runCatching {
    //    localDelete()
    //}.getOrNull()

    private suspend fun fetchFromLocal(args: Input) = kotlin.runCatching {
        localFetch(args)
    }.getOrThrow()

    private suspend fun fetchFromRemote(args: Input) = kotlin.runCatching {
        remoteFetch(args)
    }.getOrThrow().also {
        kotlin.runCatching {
            localStore(it)
            //refreshControl.refresh()
        }
    }
}