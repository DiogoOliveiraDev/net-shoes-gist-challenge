package com.example.netshoesgistchallenge.features.home.view.fragment

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.common.bind
import com.example.netshoesgistchallenge.features.gistdetails.view.GistDetailsActivity
import com.example.netshoesgistchallenge.features.home.adapter.listeners.AvatarClickListener
import com.example.netshoesgistchallenge.features.home.adapter.GistItem
import com.example.netshoesgistchallenge.features.home.adapter.listeners.GistItemClickListener
import com.example.netshoesgistchallenge.features.home.adapter.GistListAdapter
import com.example.netshoesgistchallenge.features.home.adapter.listeners.ManageFavoriteListener
import com.example.netshoesgistchallenge.features.home.viewmodel.HomeViewModel
import com.example.netshoesgistchallenge.global.BaseFragment
import com.example.netshoesgistchallenge.global.InfiniteRecyclerViewHandler
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseHomePageFragment :
    BaseFragment(),
    GistItemClickListener,
    AvatarClickListener,
    ManageFavoriteListener {

    private val rvGistItems by lazy { bind<RecyclerView>(R.id.rvGistItems) }
    protected val viewModel by sharedViewModel<HomeViewModel>()
    private lateinit var infiniteRecyclerViewHandler: InfiniteRecyclerViewHandler<
            GistItem, GistListAdapter.ViewHolder>

    protected fun fetchDataFinished() {
        infiniteRecyclerViewHandler.fetchDataFinished()
    }

    protected fun initializeRecyclerView(adapter: GistListAdapter) {
        rvGistItems.itemAnimator?.changeDuration = 0
        rvGistItems.adapter = adapter
        rvGistItems.layoutManager = LinearLayoutManager(context).apply {
            infiniteRecyclerViewHandler =  InfiniteRecyclerViewHandler(
                this,
                adapter
            ) {
                viewModel.getMoreGits()
            }

        }

        rvGistItems.addOnScrollListener(infiniteRecyclerViewHandler)
    }

    override fun onChangeFavoriteStatus(gistId: String, isAddToFavorites: Boolean) {
        viewModel.onChangeFavoriteStatus(gistId, isAddToFavorites)
    }

    override fun onItemClick(
        gistId: String,
        avatarUrl: String,
        user: String,
        language: String
    ) {
        Intent(context, GistDetailsActivity::class.java).apply {
            putExtra(
                GistDetailsActivity.GIST_ID_KEY, gistId
            )

            showGistDetailsActivity(this)
        }
    }

    private fun showGistDetailsActivity(intent: Intent) = startActivity(intent)
}
