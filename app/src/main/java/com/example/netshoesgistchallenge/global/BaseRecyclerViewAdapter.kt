package com.example.netshoesgistchallenge.global

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<I, VH : BaseRecyclerViewAdapter.BaseViewHolder<I>> : RecyclerView.Adapter<VH>() {

    init {
        initialize()
    }

    private val items: MutableList<I> = mutableListOf()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            getViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(getLayout(), parent, false)
            )

    @LayoutRes
    abstract fun getLayout() : Int

    abstract fun getViewHolder(view: View) : VH

    fun setItems(items: List<I>) {
        this.items.clear()
        items.map {
            this.items.add(it)
        }

        notifyDataSetChanged()
    }

    fun reset() {
        this.items.clear()
        notifyDataSetChanged()
    }

    private fun initialize() {
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder<I>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: I)
    }
}
