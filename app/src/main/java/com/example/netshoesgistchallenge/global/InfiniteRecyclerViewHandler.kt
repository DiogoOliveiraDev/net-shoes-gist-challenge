package com.example.netshoesgistchallenge.global

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netshoesgistchallenge.common.Constants

class InfiniteRecyclerViewHandler<I, VH : BaseRecyclerViewAdapter.BaseViewHolder<I>>(
    private val layoutManager: LinearLayoutManager,
    private val adapter: BaseRecyclerViewAdapter<I, VH>,
    private val callback: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var isLoadingNewItems = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = layoutManager.childCount
        val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
        val total = adapter.itemCount

        if (!isLoadingNewItems) {
            if ((visibleItemCount + pastVisibleItem) >= total && total >= Constants.ITEMS_PER_PAGE) {
                isLoadingNewItems = true
                callback.invoke()
            }
        }

        super.onScrolled(recyclerView, dx, dy)
    }

    fun fetchDataFinished() {
        isLoadingNewItems = false
    }
}
