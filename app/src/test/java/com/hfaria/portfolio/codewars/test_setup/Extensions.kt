package com.hfaria.portfolio.codewars.test_setup

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.lang.Thread.sleep
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/* Copyright 2019 Google LLC.
   SPDX-License-Identifier: Apache-2.0 */
fun <T> LiveData<T>.getSync(
    time: Long = 300,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getSync.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

fun <T> LiveData<T>.collect(
    action: () -> Any,
    time: Long = 300,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS
    ): List<T> {
    val emittedValues = mutableListOf<T>()

    val observer = Observer<T> { o ->
        if (o != null) {
            emittedValues.add(o)
        }
    }

    this.observeForever(observer)
    action.invoke()
    sleep(time)
    this.removeObserver(observer)

    return emittedValues
}