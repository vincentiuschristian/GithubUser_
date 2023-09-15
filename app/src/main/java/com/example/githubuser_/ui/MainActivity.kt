package com.example.githubuser_.ui


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser_.R
import com.example.githubuser_.adapter.UserAdapter
import com.example.githubuser_.databinding.ActivityMainBinding
import com.example.githubuser_.response.ItemsItem
import com.example.githubuser_.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvItem.layoutManager = layoutManager

        mainViewModel.dataUser.observe(this) { data ->
            setUserData(data)
        }

        mainViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()

                    mainViewModel.getDataUser(searchView.text.toString())
                    false
                }
        }

    }

    private fun setUserData(data: List<ItemsItem>?) {
        if (data!!.isEmpty()) {
            binding.tvUserNotFound.visibility = View.VISIBLE
            binding.rvItem.visibility = View.INVISIBLE
        } else {
            binding.rvItem.visibility = View.VISIBLE
            val adapter = UserAdapter()
            adapter.submitList(data)
            binding.rvItem.adapter = adapter
            binding.tvUserNotFound.visibility = View.INVISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

}