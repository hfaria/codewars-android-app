package com.hfaria.portfolio.codewars.ui

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment<VM>: Fragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: VM by viewModels {
        viewModelFactory
    }

}