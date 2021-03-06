package com.hfaria.portfolio.codewars.domain.base

import com.hfaria.portfolio.codewars.persistence.DataWrapper

interface ValidationResult {

    fun isValid(): Boolean
}

abstract class BaseInteractor<in PARAM, out VR : ValidationResult, out RESPONSE> {

    protected abstract fun validate(param: PARAM): VR

    protected abstract fun invoke(param: PARAM): DataWrapper<RESPONSE>

    open fun run(param: PARAM, output: OutputPort<RESPONSE, VR>) {
        try {
            val validationResult = validate(param)

            if (!validationResult.isValid()) {
                output.onInvalidInput(validationResult)
                return
            }

            output.onStartLoading()
            val response = invoke(param)
            output.onStopLoading()

            if (response.hasData()) {
                output.onSuccess(response.data!!)
            } else {
                output.onRepositoryError(response.message!!)
            }

        }  catch (throwable: Throwable) {
            output.onException(throwable)
        }
    }
}