package com.hfaria.portfolio.codewars.integration.setup

import android.app.Application
import com.hfaria.portfolio.codewars.acceptance.search_user.SearchUserAcceptanceTest
import com.hfaria.portfolio.codewars.di.AppComponent
import com.hfaria.portfolio.codewars.di.AppModule
import com.hfaria.portfolio.codewars.integration.UserNetworkTest
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
interface NetworkTestComponent : AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): NetworkTestComponent
    }

    fun inject(test: UserNetworkTest)
}