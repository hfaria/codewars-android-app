package com.hfaria.portfolio.codewars.ui.base

import com.hfaria.portfolio.codewars.domain.base.OutputPort

abstract class BasePresenter<D, V>(
    protected val state: BaseScreenState
): OutputPort<D, V> {

    override fun onRepositoryError(error: String) {
        state.showErrorMessage(error)
    }

    override fun onException(throwable: Throwable) {
        state.showErrorMessage(throwable.toString())
    }

    override fun onStartLoading() {
        state.setLoadingState(true)
    }

    override fun onStopLoading() {
        state.setLoadingState(false)
    }
}