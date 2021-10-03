package com.hfaria.portfolio.codewars.ui.search_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.databinding.SearchUserBinding
import javax.inject.Inject

class SearchUserActivity : AppCompatActivity() {

    @Inject
    lateinit var vidwModelFactory: ViewModelProvider.Factory

    private val viewModel: SearchUserViewModel by viewModels {
        vidwModelFactory
    }

    private lateinit var binding: SearchUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CodeWarsApp).appComponent
            .searchUserComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.search_user)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.user.observe(this) {
            Toast
                .makeText(this, it.toString(), Toast.LENGTH_LONG)
                .show()
        }

        viewModel.errorMessage.observe(this) {
            Toast
                .makeText(this, it, Toast.LENGTH_LONG)
                .show()
        }
    }
}