package com.hfaria.portfolio.codewars.acceptance.search_user

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.hfaria.portfolio.codewars.test_setup.DaggerTestAppComponent
import com.hfaria.portfolio.codewars.test_setup.TestAppComponent
import com.hfaria.portfolio.codewars.ui.search_user.SearchUserViewModel
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class SearchUserAcceptanceTest {

    @Inject
    lateinit var viewModel: SearchUserViewModel

    @Before
    fun setup() {
        val application = ApplicationProvider.getApplicationContext<Context>()
        val component: TestAppComponent = DaggerTestAppComponent
            .builder()
            .application(application as Application)
            .build()
        component.inject(this)
    }

    @Test
    fun test_foo() {
        viewModel.errorMessage
    }
}