package com.hfaria.portfolio.codewars.ui.user_challenges

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.databinding.UserChallengesBinding
import com.hfaria.portfolio.codewars.ui.BaseActivity
import com.hfaria.portfolio.codewars.ui.user_challenges.authored.AuthoredChallengesFragment
import com.hfaria.portfolio.codewars.ui.user_challenges.completed.CompletedChallengesFragment


class UserChallengesAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)  {
            CompletedChallengesFragment()
        } else {
            AuthoredChallengesFragment()
        }
    }
}

class UserChallengesActivity : BaseActivity<UserChallengesViewModel>() {

    val tabTitles = arrayOf("Completed", "Authored")
    lateinit var binding: UserChallengesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as CodeWarsApp).appComponent
            .userChallengesComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
        setupBinding()
        TabLayoutMediator(binding.tlUserChallenges, binding.vpUserChallenges) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.user_challenges)
        binding.lifecycleOwner = this
        val adapter = UserChallengesAdapter(this)
        binding.vpUserChallenges.adapter = adapter
    }
}