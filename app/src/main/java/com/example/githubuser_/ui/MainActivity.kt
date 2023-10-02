package com.example.githubuser_.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser_.R
import com.example.githubuser_.adapter.UserAdapter
import com.example.githubuser_.databinding.ActivityMainBinding
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.viewModel.MainViewModel
import com.example.githubuser_.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(R.drawable.github)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvItem.layoutManager = layoutManager
        binding.rvItem.setHasFixedSize(true)


        mainViewModel.dataUser.observe(this){data ->
            setUserData(data)
        }

        mainViewModel.isLoading.observe(this) { loading ->
            binding.progressBar.isVisible = loading
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    //      mainViewModel.getDataUser(searchView.text.toString())
                    mainViewModel.getUser(searchView.text.toString())
                    false
                }
        }
    }

    private fun setUserData(data: List<ItemsItem>?) {
        if (data.isNullOrEmpty()) {
            binding.rvItem.visibility = View.INVISIBLE
            binding.tvUserNotFound.visibility = View.VISIBLE
        } else {
            val adapter = UserAdapter()
            adapter.submitList(data)
            binding.rvItem.setHasFixedSize(true)
            binding.rvItem.adapter = adapter
            binding.tvUserNotFound.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_favorite -> {
            val intentFavorite = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(intentFavorite)
            true
        }

        R.id.menu_setting -> {
            val intentSetting = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(intentSetting)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}