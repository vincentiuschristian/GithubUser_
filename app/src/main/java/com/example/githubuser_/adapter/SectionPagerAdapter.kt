package com.example.githubuser_.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser_.ui.FollowerFragment

class SectionPagerAdapter(activity : AppCompatActivity) : FragmentStateAdapter(activity){
    var username : String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerFragment.ARG_POSITION, position + 1)
            putString(FollowerFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}