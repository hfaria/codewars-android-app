package com.hfaria.portfolio.codewars.ui

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseActivity<VM>: AppCompatActivity() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: VM by viewModels {
        viewModelFactory
    }

}