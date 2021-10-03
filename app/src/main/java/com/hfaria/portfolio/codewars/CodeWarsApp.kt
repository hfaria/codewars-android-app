package com.hfaria.portfolio.codewars

import android.app.Application
import com.hfaria.portfolio.codewars.di.AppComponent
import com.hfaria.portfolio.codewars.di.DaggerAppComponent

class CodeWarsApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }

}