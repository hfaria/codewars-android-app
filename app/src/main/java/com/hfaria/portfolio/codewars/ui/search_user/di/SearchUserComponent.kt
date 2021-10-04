package com.hfaria.portfolio.codewars.ui.search_user.di

import com.hfaria.portfolio.codewars.ui.search_user.SearchUserActivity
import dagger.Subcomponent

@Subcomponent(modules = [SearchUserModule::class])
interface SearchUserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchUserComponent
    }

    fun inject(activity: SearchUserActivity)
}
