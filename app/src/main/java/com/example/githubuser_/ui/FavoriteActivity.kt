package com.example.githubuser_.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuser_.R
import com.example.githubuser_.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}