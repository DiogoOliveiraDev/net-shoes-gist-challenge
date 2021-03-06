package com.example.netshoesgistchallenge.features.home.view.activity

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import androidx.viewpager2.widget.ViewPager2
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.common.bind
import com.example.netshoesgistchallenge.common.setVisibility
import com.example.netshoesgistchallenge.features.home.adapter.HomePageAdapter
import com.example.netshoesgistchallenge.features.home.viewmodel.HomeViewModel
import com.example.netshoesgistchallenge.global.BaseActivity
import com.example.netshoesgistchallenge.global.TabSelectedImpl
import com.example.netshoesgistchallenge.global.TabSelectedListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity(){

    companion object {
        const val GISTS_PAGE = 0
        const val FAVORITES_PAGE = 1
    }

    private val vpContent by lazy { bind<ViewPager2>(R.id.vpContent) }
    private val tlContent by lazy { bind<TabLayout>(R.id.tlContent) }
    private val pbInfinityList by lazy { bind<ProgressBar>(R.id.pbInfinityList) }

    private val viewModel by viewModel<HomeViewModel>()

    override fun getLayout() = R.layout.actitivty_home

    override fun initialize() {
        initializeViewPagerAndTabLayout()
        setTextStatusBar(GISTS_PAGE)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initializeViewPagerAndTabLayout() {
        viewModel.homeStateStream.observe(this){
            pbInfinityList.setVisibility(it.isLoading)
        }

        vpContent.adapter = HomePageAdapter.createItems(this)
        TabLayoutMediator(tlContent, vpContent) { tab, position ->
            vpContent.setCurrentItem(tab.position, false)
            tab.setIcon(position.toTablayoutIcon())
        }.attach()

        tlContent.addOnTabSelectedListener(TabSelectedImpl(object: TabSelectedListener{
            override fun onTabSelected(position: Int) {
                setTextStatusBar(position)
            }
        }))
    }

    private fun setTextStatusBar(position: Int) {
        supportActionBar?.setTitle(position.toStatusBarText())
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun Int.toTablayoutIcon() =
        when(this) {
            GISTS_PAGE -> R.drawable.ic_githubprofile
            FAVORITES_PAGE -> R.drawable.ic_favorite
            else -> R.drawable.search_icon
        }

    private fun Int.toStatusBarText() =
        when(this) {
            GISTS_PAGE -> R.string.status_bar_gists_menu_name
            FAVORITES_PAGE -> R.string.status_bar_favorites_menu_name
            else ->  R.string.status_bar_gists_menu_name
        }
}
