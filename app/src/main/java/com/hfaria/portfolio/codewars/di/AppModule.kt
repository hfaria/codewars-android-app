package com.hfaria.portfolio.codewars.di

import android.app.Application
import androidx.room.Room
import com.hfaria.portfolio.codewars.persistence.local.dao.*
import com.hfaria.portfolio.codewars.persistence.local.db.AppDatabase
import com.hfaria.portfolio.codewars.persistence.local.db.DATABASE_NAME
import com.hfaria.portfolio.codewars.persistence.remote.api.CODEWARS_ENDPOINT
import com.hfaria.portfolio.codewars.persistence.remote.api.CodeWarsApi
import com.hfaria.portfolio.codewars.persistence.remote.adapter.DataWrapperCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelBuilderModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideCodeWarsApi(): CodeWarsApi {
        return Retrofit.Builder()
            .baseUrl(CODEWARS_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(DataWrapperCallAdapterFactory())
            .build()
            .create(CodeWarsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }
}
