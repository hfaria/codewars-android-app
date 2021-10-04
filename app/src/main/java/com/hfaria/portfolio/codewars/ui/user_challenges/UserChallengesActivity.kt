package com.hfaria.portfolio.codewars.ui.user_challenges

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.databinding.SearchUserBinding
import com.hfaria.portfolio.codewars.databinding.UserChallengesBinding
import com.hfaria.portfolio.codewars.persistence.network.api.User
import com.hfaria.portfolio.codewars.ui.BaseActivity
import com.hfaria.portfolio.codewars.ui.challenge_challenges.CompletedChallengesAdapter
import kotlinx.coroutines.launch

class UserChallengesActivity : BaseActivity<UserChallengesViewModel>() {

    private val adapter = CompletedChallengesAdapter()
    lateinit var binding: UserChallengesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CodeWarsApp).appComponent
            .userChallengesComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
        setupBinding()
        setupAdapter()
        setupObservables()
        val username = intent.getStringExtra("username")
        if (username != null) {
            viewModel.fetchCompletedChallenges(username)
        }
    }

    fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.user_challenges)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.rvCompletedChallenges.adapter = adapter
    }

    fun setupAdapter() {
        viewModel.completedChallenges.observe(this) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    fun setupObservables() {
        viewModel.errorMessage.observe(this) {
            Toast
                .makeText(this, it, Toast.LENGTH_LONG)
                .show()
        }
    }

}