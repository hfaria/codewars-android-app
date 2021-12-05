package com.hfaria.portfolio.codewars.test_setup

import android.app.Application
import android.content.Context
import com.hfaria.portfolio.codewars.acceptance.search_user.SearchUserAcceptanceTest
import com.hfaria.portfolio.codewars.di.AppComponent
import com.hfaria.portfolio.codewars.di.AppModule
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
interface TestAppComponent : AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }

    fun inject(app: TestCodeWarsApp)

    fun inject(test: SearchUserAcceptanceTest)
}