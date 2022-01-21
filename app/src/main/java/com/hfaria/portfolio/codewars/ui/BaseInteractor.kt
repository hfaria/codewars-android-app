package com.hfaria.portfolio.codewars.ui

import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.ui.search_user.interactor.InteractorOutput

interface ValidationResult {

    fun isValid(): Boolean
}

abstract class BaseInteractor<in PARAM, out VR : ValidationResult, out RESPONSE> {

    protected abstract fun validate(param: PARAM): VR

    protected abstract fun invoke(param: PARAM): DataWrapper<RESPONSE>

    open fun run(param: PARAM, output: InteractorOutput<RESPONSE, VR>) {
        try {
            val validationResult = validate(param)

            if (!validationResult.isValid()) {
                output.onInvalidInput(validationResult)
                return
            }

            val response = invoke(param)

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