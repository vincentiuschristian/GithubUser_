package com.example.githubuser_.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser_.ui.FollowFragment

class SectionPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putString(FollowFragment.ARG_USERNAME, username)
            putInt(FollowFragment.ARG_POSITION, position + 1)
        }
        return fragment
    }
}