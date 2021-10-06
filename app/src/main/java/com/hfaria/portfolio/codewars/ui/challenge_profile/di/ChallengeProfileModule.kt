package com.hfaria.portfolio.codewars.ui.challenge_profile.di

import androidx.lifecycle.ViewModel
import com.hfaria.portfolio.codewars.di.ViewModelKey
import com.hfaria.portfolio.codewars.ui.challenge_profile.ChallengeProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChallengeProfileModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChallengeProfileViewModel::class)
    abstract fun bindViewModel(viewmodel: ChallengeProfileViewModel): ViewModel
}
