package com.hfaria.portfolio.codewars

import android.app.Application
import com.hfaria.portfolio.codewars.di.AppComponent
import com.hfaria.portfolio.codewars.di.DaggerAppComponent

open class CodeWarsApp: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = createDaggerComponent()
    }

    protected open fun createDaggerComponent(): AppComponent {
        val component = DaggerAppComponent.builder()
            .application(this)
            .build()
        component.inject(this)
        return component
    }

}