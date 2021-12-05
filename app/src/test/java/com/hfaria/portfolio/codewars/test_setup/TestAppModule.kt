package com.hfaria.portfolio.codewars.test_setup

import com.hfaria.portfolio.codewars.di.ViewModelBuilderModule
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelBuilderModule::class])
class TestAppModule {
    @Singleton
    @Provides
    fun provideCodeWarsApi(): CodeWarsApi {
        return StubCodeWarsApi()
    }

    @Singleton
    @Provides
    fun provideStubCodeWarsApi(api: CodeWarsApi): StubCodeWarsApi {
        return api as StubCodeWarsApi
    }
}