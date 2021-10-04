package com.hfaria.portfolio.codewars.ui.user_challenges.di

import androidx.lifecycle.ViewModel
import com.hfaria.portfolio.codewars.di.ViewModelKey
import com.hfaria.portfolio.codewars.ui.user_challenges.UserChallengesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserChallengesModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserChallengesViewModel::class)
    abstract fun bindViewModel(viewmodel: UserChallengesViewModel): ViewModel
}
