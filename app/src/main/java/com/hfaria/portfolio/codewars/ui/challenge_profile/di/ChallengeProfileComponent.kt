package com.hfaria.portfolio.codewars.ui.challenge_profile.di

import com.hfaria.portfolio.codewars.ui.challenge_profile.ChallengeProfileActivity
import dagger.Subcomponent

@Subcomponent(modules = [ChallengeProfileModule::class])
interface ChallengeProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ChallengeProfileComponent
    }

    fun inject(activity: ChallengeProfileActivity)
}
