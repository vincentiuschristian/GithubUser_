package com.example.githubuser_.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser_.R
import com.example.githubuser_.adapter.SectionPagerAdapter
import com.example.githubuser_.databinding.ActivityDetailBinding
import com.example.githubuser_.viewModel.DetailViewModel
import com.example.githubuser_.viewModel.DetailViewModel.Companion.username
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val KEY_DATA = "key_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text1,
            R.string.tab_text2,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.detail_user)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        username = intent.getStringExtra(KEY_DATA).toString()

        detailViewModel.detailUser.observe(this) { data ->
            if (data != null) {
                binding.apply {
                    tvUsernameDetail.text = data.login
                    tvName.text = data.name
                    tvBio.text = data.bio
                    tvFollowerDetail.text =
                        resources.getString(R.string.followers, data.followers.toString())
                    tvFollowingDetail.text =
                        resources.getString(R.string.following, data.following.toString())
                    Glide.with(root.context)
                        .load(data.avatarUrl)
                        .into(ivProfileDetail)
                }
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }
}