package com.hfaria.portfolio.codewars.ui.search_user.interactor

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.ui.search_user.SearchUserScreenState
import javax.inject.Inject

class SearchUserInteractor @Inject constructor(
    private val repository: CodeWarsRepository
) {

    enum class InvalidInput {
        EMPTY_USERNAME
    }

    suspend fun run(username: String?, output: InteractorOutput<User, InvalidInput>) {
        return if (username.isNullOrEmpty()) {
            output.onInvalidInput(InvalidInput.EMPTY_USERNAME)
        } else {
            val response = repository.getUser(username)

            if (response.hasData()) {
                output.onSuccess(response.data!!)
            } else {
                output.onRepositoryError(response.message!!)
            }
        }
    }
}