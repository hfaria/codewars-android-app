package com.hfaria.portfolio.codewars.ui.search_user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfaria.portfolio.codewars.CodeWarsApp
import com.hfaria.portfolio.codewars.databinding.SearchUserBinding
import com.hfaria.portfolio.codewars.persistence.remote.api.User
import com.hfaria.portfolio.codewars.ui.BaseFragment
import com.hfaria.portfolio.codewars.ui.user_challenges.UserChallengesActivity
import com.hfaria.portfolio.codewars.util.ToastUtil

class SearchUserFragment : BaseFragment<SearchUserViewModel>() {

    private val adapter = SearchUserAdapter { user -> handleSelectedUser(user) }
    private lateinit var binding: SearchUserBinding

    override fun onAttach(activity: Activity) {
        val appContext = activity.applicationContext
        (appContext as CodeWarsApp).appComponent
            .searchUserComponent()
            .create()
            .inject(this)
        super.onAttach(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.rvRecentUser.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()

        if (savedInstanceState == null) {
            viewModel.fetchRecentUsers()
        }
    }

    private fun setupObservables() {
        viewModel.recentUsers.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.searchedUser.observe(viewLifecycleOwner) {
            handleSelectedUser(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            ToastUtil.long(requireActivity().applicationContext, message)
        }
    }

    private fun handleSelectedUser(user: User) {
        val it = Intent(requireActivity(), UserChallengesActivity::class.java)
        it.putExtra("username", user.username)
        startActivity(it)
    }
}