package com.hfaria.portfolio.codewars.domain.user

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.domain.base.BaseInteractor
import com.hfaria.portfolio.codewars.domain.base.ValidationResult
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import javax.inject.Inject

const val MINIMUM_USERNAME_LENGTH = 3

class SearchUserInteractor @Inject constructor(
    private val repository: CodeWarsRepository
) : BaseInteractor<String, SearchUserInteractor.UsernameValidation, User>() {

    enum class UsernameValidation : ValidationResult {
        OK,
        EMPTY_USERNAME,
        SHORT_USERNAME;

        override fun isValid(): Boolean {
            return this == OK
        }
    }

    override fun validate(param: String): UsernameValidation {
        return when {
            param.isEmpty() -> {
                UsernameValidation.EMPTY_USERNAME
            }
            param.length < MINIMUM_USERNAME_LENGTH -> {
                UsernameValidation.SHORT_USERNAME
            }
            else -> {
                UsernameValidation.OK
            }
        }
    }

    override fun invoke(param: String): DataWrapper<User> =
        repository.getUser(param)
}