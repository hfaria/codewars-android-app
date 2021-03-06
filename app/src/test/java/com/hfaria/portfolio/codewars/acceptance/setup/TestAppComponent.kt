package com.hfaria.portfolio.codewars.acceptance.setup

import android.app.Application
import com.hfaria.portfolio.codewars.acceptance.search_user.SearchUserAcceptanceTest
import com.hfaria.portfolio.codewars.di.AppComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestAppModule::class,
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