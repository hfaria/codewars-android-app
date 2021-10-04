package com.hfaria.portfolio.codewars.ui.search_user.di

import androidx.lifecycle.ViewModel
import com.hfaria.portfolio.codewars.di.ViewModelKey
import com.hfaria.portfolio.codewars.ui.search_user.SearchUserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchUserModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchUserViewModel::class)
    abstract fun bindViewModel(viewmodel: SearchUserViewModel): ViewModel
}
