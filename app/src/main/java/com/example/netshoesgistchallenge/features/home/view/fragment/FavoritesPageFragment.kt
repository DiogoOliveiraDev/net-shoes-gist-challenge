package com.example.netshoesgistchallenge.features.home.view.fragment

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.features.home.adapter.listeners.GistItemClickListener
import com.example.netshoesgistchallenge.features.home.adapter.GistListAdapter
import com.example.netshoesgistchallenge.features.home.viewmodel.GistSearchMode

class FavoritesPageFragment(
    private val fragmentActivity: FragmentActivity
) : BaseHomePageFragment(),
    GistItemClickListener {

    private val adapter = GistListAdapter(
        this,
        this,
        this
    )

    override fun getLayout() = R.layout.fragment_favorites

    override fun initialize() {
        viewModel.favoritesItemListStream.observe(fragmentActivity) {
            adapter.setItems(it)
        }

        super.initializeRecyclerView(adapter)

        viewModel.getFavorites()
    }

    @SuppressLint("ShowToast")
    override fun onClickAvatar(login: String, avatarUrl: String) {
        Toast.makeText(context, R.string.filters_in_favorites_under_construction,
            Toast.LENGTH_LONG).show()
    }
}
