package com.hfaria.portfolio.codewars.acceptance.setup

import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.di.AppComponent

class TestCodeWarsApp: CodeWarsApp() {

    override fun createDaggerComponent(): AppComponent {
        val component: TestAppComponent =
            DaggerTestAppComponent.builder()
                .application(this)
                .build()
        component.inject(this)
        return component
    }
}