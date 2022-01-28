package com.hfaria.portfolio.codewars.acceptance.setup

import com.hfaria.portfolio.codewars.di.ViewModelBuilderModule
import com.hfaria.portfolio.codewars.persistence.local.dao.UserDao
import com.hfaria.portfolio.codewars.persistence.remote.RemoteDataSource
import com.hfaria.portfolio.codewars.persistence.remote.RemoteDataSourceImpl
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

    @Singleton
    @Provides
    fun provideUserDao(): UserDao {
         return StubUserDao()
    }

    @Singleton
    @Provides
    fun provideStubUserDao(dao: UserDao): StubUserDao {
        return dao as StubUserDao
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: CodeWarsApi): RemoteDataSource {
        return RemoteDataSourceImpl(api)
    }
}