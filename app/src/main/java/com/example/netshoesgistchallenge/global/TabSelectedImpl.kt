package com.example.netshoesgistchallenge.global

import com.google.android.material.tabs.TabLayout

class TabSelectedImpl(private val tabSelectedListener: TabSelectedListener) : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab?) {
        tabSelectedListener.onTabSelected(tab?.position ?: 0)
    }

    @Suppress("EmptyFunctionBlock")
    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    @Suppress("EmptyFunctionBlock")
    override fun onTabReselected(tab: TabLayout.Tab?) {

    }
}
