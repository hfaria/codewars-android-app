package com.hfaria.portfolio.codewars.ui.search_user.interactor

import com.hfaria.portfolio.codewars.persistence.Status

/*
    Data class used to encapsulate the response
    of any Interactor
 */
data class InteractorOutput<out T>(val status: Status,
                              val data: T?,
                              val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        EXCEPTION,
    }

    companion object {
        fun <T> success(data: T?): InteractorOutput<T> {
            return InteractorOutput(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T? = null): InteractorOutput<T> {
            return InteractorOutput(Status.ERROR, data, msg)
        }

        fun <T> exception(t: Throwable, data: T? = null): InteractorOutput<T> {
            return InteractorOutput(Status.EXCEPTION, data, t.message)
        }
    }

    fun hasData(): Boolean
            = (status == Status.SUCCESS && data!= null)
}