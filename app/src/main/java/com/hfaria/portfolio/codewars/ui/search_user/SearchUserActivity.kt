package com.hfaria.portfolio.codewars.ui.search_user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.databinding.SearchUserBinding
import com.hfaria.portfolio.codewars.persistence.Status
import javax.inject.Inject

class SearchUserActivity : AppCompatActivity() {

    @Inject
    lateinit var vidwModelFactory: ViewModelProvider.Factory

    private val viewModel: SearchUserViewModel by viewModels {
        vidwModelFactory
    }

    private lateinit var binding: SearchUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CodeWarsApp).appDaggerComponent
            .searchUserComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.search_user)
        binding.viewmodel = viewModel

        /*
        *    TODO: Replace visibility change here with proper data binding
        * */
        viewModel.user.observe(this) {
            if (it.status == Status.LOADING) {
                binding.cpiFetching.visibility = View.VISIBLE
            } else {
                binding.cpiFetching.visibility = View.GONE
                if (it.status == Status.SUCCESS) {
                    Toast
                        .makeText(this, it.data.toString(), Toast.LENGTH_LONG)
                        .show()
                } else if (it.status == Status.ERROR) {
                    Toast
                        .makeText(this, it.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}