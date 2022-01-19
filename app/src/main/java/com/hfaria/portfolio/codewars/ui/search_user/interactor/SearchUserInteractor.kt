package com.hfaria.portfolio.codewars.ui.search_user.interactor

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.ui.search_user.SearchUserScreenState
import javax.inject.Inject

class SearchUserInteractor @Inject constructor(
    private val repository: CodeWarsRepository
) {

    suspend fun run(username: String?) : InteractorOutput<User> {
        return if (username.isNullOrEmpty()) {
            InteractorOutput.error(SearchUserScreenState.ERROR_EMPTY_USERNAME)
        } else {
            val response = repository.getUser(username)

            if (response.hasData()) {
                InteractorOutput.success(response.data)
            } else {
                InteractorOutput.error(response.message!!, null)
            }
        }
    }
}