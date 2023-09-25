package com.example.githubuser_.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser_.database.FavoriteDao
import com.example.githubuser_.database.FavoriteDatabase
import com.example.githubuser_.database.UserEntity
import com.example.githubuser_.databinding.ItemUserBinding
import com.example.githubuser_.ui.DetailActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteAdapter(
    private val activity: Activity
) :
    ListAdapter<UserEntity, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var favoriteDatabase: FavoriteDao

    inner class MyViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: UserEntity) {
            with(binding) {
                tvUsername.text = favorite.username
                Glide.with(itemView.context)
                    .load(favorite.avatarUrl)
                    .into(ivImageProfile)
                    .clearOnDetach()

                itemView.setOnClickListener {
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.KEY_DATA, favorite.username)
                    itemView.context.startActivity(intentDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)

        val btnDelete = holder.binding.btnDeleteFavorite
        btnDelete.visibility = View.VISIBLE
        btnDelete.setOnClickListener {
            favoriteDatabase = FavoriteDatabase.getDatabase(activity).favoriteDao()
            GlobalScope.launch {
                favoriteDatabase.delete(favorite.username.toString())
            }
            Toast.makeText(activity, "${favorite.username} removed", Toast.LENGTH_SHORT)
                .show()
        }
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
