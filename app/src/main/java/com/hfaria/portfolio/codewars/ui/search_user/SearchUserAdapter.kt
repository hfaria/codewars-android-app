package com.hfaria.portfolio.codewars.ui.search_user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hfaria.portfolio.codewars.databinding.RecentUserItemBinding
import com.hfaria.portfolio.codewars.persistence.remote.api.User

class SearchUserAdapter(
    private val onClick: (User) -> Unit
) : ListAdapter<User, SearchUserAdapter.UserViewHolder>(UserDiffCallback) {

    object UserDiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.username == newItem.username
        }
    }

    class UserViewHolder(val binding: RecentUserItemBinding, val onClick: (User) -> Unit)
    : RecyclerView.ViewHolder(binding.root) {

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
            binding.user = user
        }
    }

    /* Creates and inflates view and return UserViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecentUserItemBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding, onClick)
    }

    /* Gets current user and uses it to bind view. */
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}
