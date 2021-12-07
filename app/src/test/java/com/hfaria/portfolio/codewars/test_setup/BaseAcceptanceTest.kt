package com.hfaria.portfolio.codewars.test_setup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseAcceptanceTest {

    // LiveData adapter code
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Coroutines adapter code
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    // Dependency Injection
    @Before
    fun setupDI() {
        val application = TestCodeWarsApp()
        val component: TestAppComponent = DaggerTestAppComponent
            .builder()
            .application(application)
            .build()
        injectTest(component)
    }

    abstract fun injectTest(component: TestAppComponent)
}