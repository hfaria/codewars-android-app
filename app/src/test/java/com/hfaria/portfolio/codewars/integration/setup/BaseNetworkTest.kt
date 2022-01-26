package com.hfaria.portfolio.codewars.integration.setup

import com.hfaria.portfolio.codewars.acceptance.setup.TestCodeWarsApp
import org.junit.Before

abstract class BaseNetworkTest {

    @Before
    fun setupDI() {
        val application = TestCodeWarsApp()
        val component = DaggerNetworkTestComponent
            .builder()
            .application(application)
            .build()
        injectTest(component)
    }

    abstract fun injectTest(component: NetworkTestComponent)
}