package com.example.githubuser_.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser_.R
import com.example.githubuser_.databinding.ActivitySettingBinding
import com.example.githubuser_.setting_preference.SettingViewModel
import com.example.githubuser_.viewModel.ViewModelFactory

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var setting: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.settings)

        val switchTheme = binding.switchTheme

        setting = ViewModelFactory.getInstance(application)
        val settingViewModel =
            ViewModelProvider(this, setting)[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                binding.tvDarkMod.text = resources.getString(R.string.dark_mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                binding.tvDarkMod.text = resources.getString(R.string.light_mode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }

    }
}