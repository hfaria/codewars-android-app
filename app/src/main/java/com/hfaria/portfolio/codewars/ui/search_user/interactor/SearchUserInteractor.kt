package com.hfaria.portfolio.codewars.ui.search_user.interactor

import com.hfaria.portfolio.codewars.domain.User
import com.hfaria.portfolio.codewars.persistence.CodeWarsRepository
import com.hfaria.portfolio.codewars.persistence.DataWrapper
import com.hfaria.portfolio.codewars.ui.search_user.SearchUserScreenState
import javax.inject.Inject

class SearchUserInteractor @Inject constructor(
    private val repository: CodeWarsRepository
) {

    suspend fun run(username: String?) : DataWrapper<User> {
        return if (username.isNullOrEmpty()) {
            DataWrapper.error(SearchUserScreenState.ERROR_EMPTY_USERNAME)
        } else {
            repository.getUser(username)
        }
    }
}