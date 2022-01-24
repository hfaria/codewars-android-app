package com.hfaria.portfolio.codewars.ui.search_user.interactor

/*
 *  Generic Interactor Use Case Output Port
 */
interface InteractorOutput<in D, in R> {

    fun onInvalidInput(reason: R)
    fun onStartLoading()
    fun onStopLoading()
    fun onRepositoryError(error: String)
    fun onException(throwable: Throwable)
    fun onSuccess(data: D)

}