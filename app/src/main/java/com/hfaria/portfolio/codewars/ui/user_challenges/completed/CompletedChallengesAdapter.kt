package com.hfaria.portfolio.codewars.ui.challenge_challenges

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.persistence.network.api.CompletedChallenge


object ChallengeDiffCallback: DiffUtil.ItemCallback<CompletedChallenge>() {
    override fun areItemsTheSame(oldItem: CompletedChallenge, newItem: CompletedChallenge): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CompletedChallenge, newItem: CompletedChallenge): Boolean {
        return oldItem.id == newItem.id
    }
}

class CompletedChallengesAdapter()
    : PagingDataAdapter<CompletedChallenge, CompletedChallengesAdapter.ViewHolder>(ChallengeDiffCallback) {

    class ViewHolder(itemView: View, val onClick: (CompletedChallenge) -> Unit = {}) :
        RecyclerView.ViewHolder(itemView) {
        private val tvChallengeName: TextView = itemView.findViewById(R.id.tv_challenge_name)
        private var curChallenge: CompletedChallenge? = null

        //init {
        //    itemView.setOnClickListener {
        //        currentCompletedChallenge?.let {
        //            onClick(it)
        //        }
        //    }
        //}

        fun bind(challenge: CompletedChallenge?) {
            curChallenge = challenge
            tvChallengeName.text = challenge?.name
        }
    }

    /* Creates and inflates view and return CompletedChallengeViewHolder. */
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