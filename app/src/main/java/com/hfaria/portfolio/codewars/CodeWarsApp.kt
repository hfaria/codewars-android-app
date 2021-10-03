package com.hfaria.portfolio.codewars

import android.app.Application
import com.hfaria.portfolio.codewars.di.AppComponent
import com.hfaria.portfolio.codewars.di.DaggerAppComponent

class CodeWarsApp: Application() {

    val appDaggerComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}