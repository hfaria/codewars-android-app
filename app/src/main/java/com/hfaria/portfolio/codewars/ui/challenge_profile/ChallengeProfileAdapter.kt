package com.hfaria.portfolio.codewars.ui.search_user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hfaria.portfolio.codewars.R
import com.hfaria.portfolio.codewars.domain.User

class ChallengeProfileAdapter(
    private val onClick: (User) -> Unit
) : ListAdapter<User, ChallengeProfileAdapter.UserViewHolder>(UserDiffCallback) {

    object UserDiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    class UserViewHolder(itemView: View, val onClick: (User) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val tvUserName: TextView = itemView.findViewById(R.id.tv_username)
        private var currentUser: User? = null

        init {
            itemView.setOnClickListener {
                currentUser?.let {
                    onClick(it)
                }
            }
        }

        fun bind(user: User) {
            currentUser =  user
            tvUserName.text = user.username
        }
    }

    /* Creates and inflates view and return UserViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_user_item, parent, false)
        return UserViewHolder(view, onClick)
    }

    /* Gets current user and uses it to bind view. */
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}
