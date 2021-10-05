package com.hfaria.portfolio.codewars.util

import java.util.concurrent.TimeUnit

object TimeUtil {

    fun nowInSeconds(): Int {
        val timeInMillis = System.currentTimeMillis()
        return TimeUnit.MILLISECONDS.toSeconds(timeInMillis).toInt()
    }
}