package com.example.githubuser_.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser_.R
import com.example.githubuser_.adapter.SectionPagerAdapter
import com.example.githubuser_.database.FavoriteDao
import com.example.githubuser_.database.FavoriteDatabase
import com.example.githubuser_.database.UserEntity
import com.example.githubuser_.databinding.ActivityDetailBinding
import com.example.githubuser_.viewModel.DetailViewModel
import com.example.githubuser_.viewModel.DetailViewModel.Companion.username
import com.example.githubuser_.viewModel.FavoriteViewModel
import com.example.githubuser_.viewModel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }
    private lateinit var database: FavoriteDao

    //   private var username = ""
    private var avatar = ""

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
            avatar = data.avatarUrl.toString()
            if (data != null) {
                binding.apply {
                    tvUsernameDetail.text = data.login
                    tvName.text = data.name
                    tvBio.text = data.bio
                    tvFollowerDetail.text =
                        resources.getString(R.string.followers, data.followers.toString())
                    tvFollowingDetail.text =
                        resources.getString(R.string.following, data.following.toString())
                    tvRepositories.text =
                        resources.getString(R.string.repositories, data.publicRepos.toString())
                    Glide.with(root.context)
                        .load(data.avatarUrl)
                        .into(ivProfileDetail)
                }
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.fabShare.setOnClickListener {
            detailViewModel.detailUser.observe(this){
                val githubUrl = it.htmlUrl.toString()
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, githubUrl)
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share to: "))
            }
        }

        binding.fabFavorite.setOnClickListener {
            val database = FavoriteDatabase.getDatabase(applicationContext).favoriteDao()

            if (!database.isFavorite(username)) {
                favoriteIcon(true)
                val newFavorite = UserEntity(
                    username = username,
                    avatarUrl = avatar,
                    isFavorite = true
                )
                favoriteViewModel.saveFavorite(newFavorite)
            } else {
                favoriteIcon(false)
                favoriteViewModel.deleteFavorite(username)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isFavorite()
    }

    private fun isFavorite() {
        database = FavoriteDatabase.getDatabase(applicationContext).favoriteDao()
        val isFavoriteUser = database.isFavorite(username)
        favoriteIcon(isFavoriteUser)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_favorite -> {
            val intentFavorite = Intent(this@DetailActivity, FavoriteActivity::class.java)
            startActivity(intentFavorite)
            true
        }

        R.id.menu_setting -> {
            val intentSetting = Intent(this@DetailActivity, SettingActivity::class.java)
            startActivity(intentSetting)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteIcon(favorite: Boolean) {
        binding.fabFavorite.setImageDrawable(
            if (favorite) {
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.baseline_favorite_24
                )
            } else {
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.baseline_favorite_border_24
                )
            }
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

}