package com.hfaria.portfolio.codewars.ui.search_user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.databinding.SearchUserBinding
import com.hfaria.portfolio.codewars.persistence.remote.api.User
import com.hfaria.portfolio.codewars.ui.BaseActivity
import com.hfaria.portfolio.codewars.ui.user_challenges.UserChallengesActivity
import com.hfaria.portfolio.codewars.util.ToastUtil

class SearchUserActivity : BaseActivity<SearchUserViewModel>() {

    val adapter = SearchUserAdapter { user -> handleSelectedUser(user) }
    lateinit var binding: SearchUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CodeWarsApp).appComponent
            .searchUserComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
        setupBinding()
        setupObservables()
        viewModel.fetchRecentUsers()
    }

    fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.search_user)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.rvRecentUser.adapter = adapter
    }

    fun setupObservables() {
        viewModel.recentUsers.observe(this, {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.searchedUser.observe(this) {
            handleSelectedUser(it)
        }

        viewModel.errorMessage.observe(this) {
            ToastUtil.long(this, it)
        }
    }

    private fun handleSelectedUser(user: User) {
        val it = Intent(this, UserChallengesActivity::class.java)
        it.putExtra("username", user.username)
        startActivity(it)
    }
}