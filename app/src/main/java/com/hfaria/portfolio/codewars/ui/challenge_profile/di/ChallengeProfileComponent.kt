package com.hfaria.portfolio.codewars.ui.challenge_profile.di

import com.hfaria.portfolio.codewars.ui.challenge_profile.ChallengeProfileFragment
import dagger.Subcomponent

@Subcomponent(modules = [ChallengeProfileModule::class])
interface ChallengeProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ChallengeProfileComponent
    }

    fun inject(activity: ChallengeProfileFragment)
}
