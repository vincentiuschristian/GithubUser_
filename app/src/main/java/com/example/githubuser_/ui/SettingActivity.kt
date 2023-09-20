package com.example.githubuser_.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuser_.R
import com.example.githubuser_.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}