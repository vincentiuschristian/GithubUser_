package com.example.githubuser_.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser_.databinding.ItemUserBinding
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.ui.DetailActivity

class UserAdapter : ListAdapter<ItemsItem, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private fun ImageView.loadImage(url: String?) {
            Glide.with(this.context)
                .load(url)
                .centerCrop()
                .into(this)
                .clearOnDetach()
        }

        fun bind(data: ItemsItem) {
            binding.apply {
                tvUsername.text = data.login
                ivImageProfile.loadImage(
                    url = data.avatarUrl
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val dataUser = getItem(position)
        holder.bind(dataUser)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.KEY_DATA, dataUser.login)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
