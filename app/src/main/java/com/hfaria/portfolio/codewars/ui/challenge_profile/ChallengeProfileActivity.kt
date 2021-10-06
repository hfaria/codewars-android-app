package com.hfaria.portfolio.codewars.ui.challenge_profile

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.databinding.ChallengeProfileBinding
import com.hfaria.portfolio.codewars.ui.BaseActivity

class ChallengeProfileActivity : BaseActivity<ChallengeProfileViewModel>() {

    lateinit var binding: ChallengeProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CodeWarsApp).appComponent
            .challengeProfileComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
        setupBinding()
        setupObservables()
        fetchProfile()
    }

    fun fetchProfile() {
        val challengeId = intent.getStringExtra("challengeId")
        if (challengeId != null) {
            viewModel.fetchProfile(challengeId)
        }
    }

    fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.challenge_profile)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
    }

    fun setupObservables() {
        viewModel.errorMessage.observe(this) {
            Toast
                .makeText(this, it, Toast.LENGTH_LONG)
                .show()
        }
    }
}