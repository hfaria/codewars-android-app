package com.hfaria.portfolio.codewars.ui.challenge_challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.persistence.network.api.AuthoredChallenge

class AuthoredChallengesAdapter()
    : ListAdapter<AuthoredChallenge, AuthoredChallengesAdapter.ViewHolder>(ChallengeDiffCallback) {

    object ChallengeDiffCallback: DiffUtil.ItemCallback<AuthoredChallenge>() {
        override fun areItemsTheSame(oldItem: AuthoredChallenge, newItem: AuthoredChallenge): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: AuthoredChallenge, newItem: AuthoredChallenge): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ViewHolder(itemView: View, val onClick: (AuthoredChallenge) -> Unit = {}) :
        RecyclerView.ViewHolder(itemView) {
        private val tvChallengeName: TextView = itemView.findViewById(R.id.tv_challenge_name)
        private var curChallenge: AuthoredChallenge? = null

        //init {
        //    itemView.setOnClickListener {
        //        currentAuthoredChallenge?.let {
        //            onClick(it)
        //        }
        //    }
        //}

        fun bind(challenge: AuthoredChallenge?) {
            curChallenge = challenge
            tvChallengeName.text = challenge?.name
        }
    }

    /* Creates and inflates view and return AuthoredChallengeViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.completed_challenges_item, parent, false)
        return ViewHolder(view)
    }

    /* Gets current user and uses it to bind view. */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val challenge = getItem(position)
        holder.bind(challenge)
    }
}