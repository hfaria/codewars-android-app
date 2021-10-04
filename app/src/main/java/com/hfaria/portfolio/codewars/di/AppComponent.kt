package com.hfaria.portfolio.codewars.di

import android.app.Application
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.ui.search_user.di.SearchUserComponent
import com.hfaria.portfolio.codewars.ui.user_challenges.di.UserChallengesComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(codeWarsApp: CodeWarsApp)

    fun userChallengesComponent(): UserChallengesComponent.Factory
    fun searchUserComponent(): SearchUserComponent.Factory
}
