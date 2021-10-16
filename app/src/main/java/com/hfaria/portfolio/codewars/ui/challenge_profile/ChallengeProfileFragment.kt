package com.hfaria.portfolio.codewars.ui.challenge_profile

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.databinding.ChallengeProfileBinding
import com.hfaria.portfolio.codewars.ui.BaseFragment
import com.hfaria.portfolio.codewars.util.ToastUtil

class ChallengeProfileFragment : BaseFragment<ChallengeProfileViewModel>() {

    lateinit var binding: ChallengeProfileBinding

    override fun onAttach(activity: Activity) {
        val appContext = activity.applicationContext
        (appContext as CodeWarsApp).appComponent
            .challengeProfileComponent()
            .create()
            .inject(this)
        super.onAttach(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChallengeProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()

        if (savedInstanceState == null) {
            fetchProfile()
        }
    }

    private fun fetchProfile() {
        val challengeId = requireActivity().intent.getStringExtra("challengeId")
        if (challengeId != null) {
            viewModel.fetchProfile(challengeId)
        }
    }

    private fun setupObservables() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            ToastUtil.long(requireActivity(), it)
        }
    }
}