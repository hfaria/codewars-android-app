package com.hfaria.portfolio.codewars.domain.base

/*
 *  Generic Interactor Use Case Output Port
 */
interface OutputPort<in D, in R> {

    fun onInvalidInput(reason: R)
    fun onStartLoading()
    fun onStopLoading()
    fun onRepositoryError(error: String)
    fun onException(throwable: Throwable)
    fun onSuccess(data: D)

}