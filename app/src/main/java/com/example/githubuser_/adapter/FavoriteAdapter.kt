package com.example.githubuser_.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser_.database.FavoriteDao
import com.example.githubuser_.database.FavoriteDatabase
import com.example.githubuser_.database.UserEntity
import com.example.githubuser_.databinding.ItemUserBinding
import com.example.githubuser_.helper.FavoriteDiffCallback
import com.example.githubuser_.ui.DetailActivity

class FavoriteAdapter(
    private val activity: Activity
) :
    ListAdapter<UserEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var database: FavoriteDao
    private val listFavorite = ArrayList<UserEntity>()
    fun setFavoriteUser(list: List<UserEntity>) {
        val diffCallback = FavoriteDiffCallback(listFavorite, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: UserEntity) {
            with(binding) {
                tvUsername.text = favorite.username
                Glide.with(itemView.context)
                    .load(favorite.avatarUrl)
                    .into(ivImageProfile)
                btnDeleteFavorite.visibility = View.VISIBLE
                btnDeleteFavorite.setOnClickListener {
                    database = FavoriteDatabase.getDatabase(activity).favoriteDao()
                    database.delete(favorite.username.toString())
                }
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.KEY_DATA, favorite.username)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> =
            object : DiffUtil.ItemCallback<UserEntity>() {
                override fun areItemsTheSame(
                    oldItem: UserEntity,
                    newItem: UserEntity
                ): Boolean {
                    return oldItem.username == newItem.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: UserEntity,
                    newItem: UserEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
