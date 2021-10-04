package com.hfaria.portfolio.codewars.ui.user_challenges.completed

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.databinding.CompletedChallengesBinding
import com.hfaria.portfolio.codewars.ui.BaseFragment
import com.hfaria.portfolio.codewars.ui.challenge_challenges.CompletedChallengesAdapter
import com.hfaria.portfolio.codewars.ui.user_challenges.UserChallengesViewModel
import kotlinx.coroutines.launch

class CompletedChallengesFragment : BaseFragment<UserChallengesViewModel>() {

    val adapter = CompletedChallengesAdapter()
    lateinit var binding: CompletedChallengesBinding

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
        binding = CompletedChallengesBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.rvCompletedChallenges.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        val username = activity?.intent?.getStringExtra("username")
        if (username != null) {
            viewModel.fetchCompletedChallenges(username)
        }
    }

    fun setupAdapter() {
        viewModel.completedChallenges.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }
}