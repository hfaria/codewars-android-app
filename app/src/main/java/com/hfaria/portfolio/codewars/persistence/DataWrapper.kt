package com.hfaria.portfolio.codewars.persistence

enum class Status {
    SUCCESS,
    ERROR,
}

/*
    Data class used to encapsulate any response
    to a data request.

    Could be used by any kind of data source
    to represent the result of a given data request.
 */
data class DataWrapper<out T>(val status: Status,
                           val data: T?,
                           val message: String?) {
    companion object {
        fun <T> success(data: T?): DataWrapper<T> {
            return DataWrapper(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): DataWrapper<T> {
            return DataWrapper(Status.ERROR, data, msg)
        }
    }

    fun hasData(): Boolean
        = (status == Status.SUCCESS && data!= null)
}