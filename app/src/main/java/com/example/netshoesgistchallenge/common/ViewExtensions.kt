package com.example.netshoesgistchallenge.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netshoesgistchallenge.R

inline fun <reified T : View> View.bind(id: Int): T = findViewById(id)
inline fun <reified T : View> Activity.bind(id: Int): T = findViewById(id)
inline fun <reified T : View> Fragment.bind(id: Int): T = view?.findViewById(id) as T
inline fun <reified T : View> RecyclerView.ViewHolder.bind(id: Int): T = itemView.findViewById(id) as T

fun ImageView.loadImage(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_githubprofile)
        .fallback(R.drawable.ic_githubprofile)
        .dontAnimate()
        .into(this)
}

fun View.setVisibility(bool: Boolean) =
        if (bool) visibility = View.VISIBLE
        else visibility = View.GONE

fun View.invertVisibility() =
        if (visibility == View.VISIBLE) visibility = View.GONE
        else visibility = View.VISIBLE

