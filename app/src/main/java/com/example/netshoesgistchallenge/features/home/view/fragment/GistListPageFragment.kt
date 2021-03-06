package com.example.netshoesgistchallenge.features.home.view.fragment

import android.util.Log
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.common.bind
import com.example.netshoesgistchallenge.common.setVisibility
import com.example.netshoesgistchallenge.features.home.adapter.listeners.FetchFromInternetUserGistsListener
import com.example.netshoesgistchallenge.features.home.adapter.listeners.FilterUserCurrentGistsListener
import com.example.netshoesgistchallenge.features.home.adapter.GistListAdapter
import com.example.netshoesgistchallenge.features.home.state.HomeState
import com.example.netshoesgistchallenge.features.home.view.dialog.GistByUserFilterDialog
import com.example.netshoesgistchallenge.features.home.viewmodel.GistSearchMode
import com.facebook.shimmer.ShimmerFrameLayout

class GistListPageFragment(
    private val fragmentActivity: FragmentActivity
) : BaseHomePageFragment(),
    FilterUserCurrentGistsListener,
    FetchFromInternetUserGistsListener {

    private val srLoading by lazy { bind<SwipeRefreshLayout>(R.id.srLoading) }
    private val shimmer  by lazy {  bind<ShimmerFrameLayout>(R.id.shimmer) }
    private val flError by lazy {  bind<FrameLayout>(R.id.flError) }
    private val adapter = GistListAdapter(this, this, this)

    override fun getLayout() = R.layout.fragment_gist_list

    override fun initialize() {
        observeStreamData()
        super.initializeRecyclerView(adapter)

        shimmer.startLayoutAnimation()

        srLoading.setOnRefreshListener {
            adapter.reset()
            viewModel.onSwipeRefresh(srLoading.isRefreshing)
        }
    }

    override fun observeStreamData() {
        viewModel.gistItemListStream.observe(fragmentActivity) {
            Log.i("LOG:: ", "List from service ${it.size}")
            adapter.setItems(it)
        }

        viewModel.homeStateStream.observe(fragmentActivity) {
            onStateUpdate(it)
        }

        viewModel.getGists(GistSearchMode.NEW_GISTS)
    }

    private fun onStateUpdate(it: HomeState) {
        srLoading.isRefreshing = false
        if (!it.isLoading) {
            fetchDataFinished()
        }
        manageShimmer(it.shimmerVisibility)
        flError.setVisibility(it.errorLayoutVisible)
    }

    private fun manageShimmer(showShimmer: Boolean) {
        if (showShimmer){
            shimmer.showShimmer(true)
            shimmer.startLayoutAnimation()
        } else {
            shimmer.hideShimmer()
        }
    }

    override fun onClickAvatar(login: String, avatarUrl: String) {
        GistByUserFilterDialog.create(login, avatarUrl).apply {
            filterListener = this@GistListPageFragment
            fetchFromInternetListener = this@GistListPageFragment
        }.show(parentFragmentManager, "")
    }

    override fun onClickFilterUserCurrentGistsListener(user: String) {
        viewModel.filterCurrentUserOnList(user)
    }

    override fun onClickFetchFromInternetUserGistsListener(user: String) {
        viewModel.getGists(GistSearchMode.BY_USER, user)
    }
}
