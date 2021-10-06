package com.hfaria.portfolio.codewars.ui.user_challenges.authored

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.databinding.AuthoredChallengesBinding
import com.hfaria.portfolio.codewars.persistence.remote.api.AuthoredChallenge
import com.hfaria.portfolio.codewars.ui.BaseFragment
import com.hfaria.portfolio.codewars.ui.challenge_profile.ChallengeProfileActivity
import com.hfaria.portfolio.codewars.ui.user_challenges.UserChallengesViewModel
import kotlinx.coroutines.launch

class AuthoredChallengesFragment : BaseFragment<UserChallengesViewModel>() {

    val adapter = AuthoredChallengesAdapter { challenge -> handleSelectedChallenge(challenge) }

    override fun onAttach(activity: Activity) {
        val appContext = activity.applicationContext
        (appContext as CodeWarsApp).appComponent
            .userChallengesComponent()
            .create()
            .inject(this)
        super.onAttach(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AuthoredChallengesBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvAuthoredChallenges.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        val username = activity?.intent?.getStringExtra("username")
        if (username != null) {
            viewModel.fetchAuthoredChallenges(username)
        }
    }

    fun setupAdapter() {
        viewModel.authoredChallenges.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitList(it)
            }
        }
    }

    private fun handleSelectedChallenge(challenge: AuthoredChallenge) {
        val it = Intent(activity, ChallengeProfileActivity::class.java)
        it.putExtra("challengeId", challenge.id)
        startActivity(it)
    }
}