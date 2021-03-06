package com.example.netshoesgistchallenge.features.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.netshoesgistchallenge.features.home.view.fragment.FavoritesPageFragment
import com.example.netshoesgistchallenge.features.home.view.fragment.GistListPageFragment

class HomePageAdapter private constructor(
        fragmentActivity: FragmentActivity,
        private val pagesList: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = pagesList.size

    override fun createFragment(position: Int) = pagesList[position]

    companion object {
        fun createItems(fragmentActivity: FragmentActivity) =
                HomePageAdapter(
                        fragmentActivity,
                        getHomePages(fragmentActivity)
                )

        private fun getHomePages(
                fragmentActivity: FragmentActivity
        ) = listOf<Fragment>(
            GistListPageFragment(fragmentActivity),
            FavoritesPageFragment(fragmentActivity)
        )
    }
}
