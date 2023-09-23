package com.example.githubuser_.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser_.R
import com.example.githubuser_.adapter.FavoriteAdapter
import com.example.githubuser_.databinding.ActivityFavoriteBinding
import com.example.githubuser_.viewModel.FavoriteViewModel
import com.example.githubuser_.viewModel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private lateinit var favoriteAdapter: FavoriteAdapter

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.favorite_user)

        favoriteAdapter = FavoriteAdapter(this)
        binding?.rvItemFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvItemFavorite?.adapter = favoriteAdapter
    }

    override fun onResume() {
        super.onResume()
        showDataFavorite()
    }

    private fun showDataFavorite() {
        favoriteViewModel.getFavoriteUser().observe(this) { users ->
            if (users != null){
                loading(false)
                binding?.tvTidakAdaFavorite?.visibility = View.GONE
                favoriteAdapter.submitList(users)
            } else {
                loading(true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}