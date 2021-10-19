package com.hfaria.portfolio.codewars.ui.user_challenges.completed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.persistence.local.entity.CompletedChallengeEntity

class CompletedChallengesAdapter(
    private val onClick: (CompletedChallengeEntity) -> Unit
) : PagingDataAdapter<CompletedChallengeEntity, CompletedChallengesAdapter.ViewHolder>(ChallengeDiffCallback) {

    object ChallengeDiffCallback: DiffUtil.ItemCallback<CompletedChallengeEntity>() {
        override fun areItemsTheSame(oldItem: CompletedChallengeEntity, newItem: CompletedChallengeEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CompletedChallengeEntity, newItem: CompletedChallengeEntity): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView: View, val onClick: (CompletedChallengeEntity) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val tvChallengeName: TextView = itemView.findViewById(R.id.tv_challenge_name)
        private var curChallenge: CompletedChallengeEntity? = null

        init {
            itemView.setOnClickListener {
                curChallenge?.let {
                    onClick(it)
                }
            }
        }

        fun bind(challenge: CompletedChallengeEntity?) {
            curChallenge = challenge
            tvChallengeName.text = challenge?.name
        }
    }

    /* Creates and inflates view and return CompletedChallengeViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.completed_challenges_item, parent, false)
        return ViewHolder(view, onClick)
    }

    /* Gets current user and uses it to bind view. */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val challenge = getItem(position)
        holder.bind(challenge)
    }
}