package com.hfaria.portfolio.codewars.ui.user_challenges

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.databinding.UserChallengesBinding
import com.hfaria.portfolio.codewars.ui.BaseFragment
import com.hfaria.portfolio.codewars.ui.user_challenges.authored.AuthoredChallengesFragment
import com.hfaria.portfolio.codewars.ui.user_challenges.completed.CompletedChallengesFragment
import com.hfaria.portfolio.codewars.util.ToastUtil

class UserChallengesFragment : BaseFragment<UserChallengesViewModel>() {

    class UserChallengesAdapter(activity: FragmentActivity) :
        FragmentStateAdapter(activity) {

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) {
                CompletedChallengesFragment()
            } else {
                AuthoredChallengesFragment()
            }
        }
    }

    val tabTitles = arrayOf("Completed", "Authored")
    lateinit var binding: UserChallengesBinding

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
        binding = UserChallengesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val adapter = UserChallengesAdapter(requireActivity())
        binding.vpUserChallenges.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
        setupActionBar()
        setupObservables()
    }

    private fun setupActionBar() {
        val username = requireActivity().intent.getStringExtra("username")
        val supportActionBar = requireActivity().actionBar
        if (username != null && supportActionBar != null) {
            supportActionBar.title = "$username Challenges"
        }
    }

    private fun setupTabs() {
        TabLayoutMediator(binding.tlUserChallenges, binding.vpUserChallenges) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun setupObservables() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            ToastUtil.long(requireActivity(), it)
        }
    }
}