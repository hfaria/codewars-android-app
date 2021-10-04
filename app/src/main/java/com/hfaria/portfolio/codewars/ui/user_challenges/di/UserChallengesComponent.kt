package com.hfaria.portfolio.codewars.ui.user_challenges.di

import com.hfaria.portfolio.codewars.ui.user_challenges.UserChallengesActivity
import com.hfaria.portfolio.codewars.ui.user_challenges.authored.AuthoredChallengesFragment
import com.hfaria.portfolio.codewars.ui.user_challenges.completed.CompletedChallengesFragment
import dagger.Subcomponent

@Subcomponent(modules = [UserChallengesModule::class])
interface UserChallengesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserChallengesComponent
    }

    fun inject(activity: UserChallengesActivity)
    fun inject(fragment: AuthoredChallengesFragment)
    fun inject(fragment: CompletedChallengesFragment)
}
