package com.polije.githubusersapps.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.polije.githubusersapps.DetailActivity
import com.polije.githubusersapps.FragmentTabView.FollowersFragment
import com.polije.githubusersapps.FragmentTabView.FollowingFragment


class TabAdapter(private val activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val username = activity.intent.getStringExtra("login") ?: ""
        return when (position) {
            0 -> FollowersFragment.newInstance(username)
            1 -> FollowingFragment.newInstance(username)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}