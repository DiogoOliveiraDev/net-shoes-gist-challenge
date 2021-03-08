package com.example.netshoesgistchallenge.global

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter private constructor(
        fragmentActivity: FragmentActivity,
        private val pagesList: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = pagesList.size

    override fun createFragment(position: Int) = pagesList[position]

    companion object {
        fun createPages(
            fragmentActivity: FragmentActivity,
            fragments: List<Fragment>
        ) =
            ViewPagerAdapter(
                fragmentActivity,
                fragments
            )
    }
}
