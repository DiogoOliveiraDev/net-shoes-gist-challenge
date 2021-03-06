package com.example.netshoesgistchallenge.features.home.view.dialog

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.common.bind
import com.example.netshoesgistchallenge.common.loadImage
import com.example.netshoesgistchallenge.features.home.adapter.listeners.FetchFromInternetUserGistsListener
import com.example.netshoesgistchallenge.features.home.adapter.listeners.FilterUserCurrentGistsListener
import com.example.netshoesgistchallenge.global.BaseDialog

class GistByUserFilterDialog private constructor(
    val user: String,
    val avatarUrl: String
) : BaseDialog() {

    lateinit var filterListener: FilterUserCurrentGistsListener
    lateinit var fetchFromInternetListener: FetchFromInternetUserGistsListener

    private val ivAvatar by lazy { bind<ImageView>(R.id.ivAvatar) }
    private val tvUser by lazy { bind<TextView>(R.id.tvUser) }
    private val tvDescription by lazy { bind<TextView>(R.id.tvDescription) }
    private val btFetchFromInternet by lazy { bind<Button>(R.id.btFetchFromInternet) }
    private val btFilterFromList by lazy { bind<Button>(R.id.btFilterFromList) }

    override fun getLayout() = R.layout.dialog_gist_by_user_filter_layout

    override fun initialize() {
        context?.let {
            ivAvatar.loadImage(it, avatarUrl)
        }

        tvUser.text = user
        tvDescription.text = String.format(
            resources.getString(R.string.you_can_filter_or_search
            ), user
        )

        btFetchFromInternet.setOnClickListener{
            fetchFromInternetListener.onClickFetchFromInternetUserGistsListener(user)
            dismiss()
        }

        btFilterFromList.setOnClickListener{
            filterListener.onClickFilterUserCurrentGistsListener(user)
            dismiss()
        }
    }

    companion object {
        fun create(user: String, avatarUrl: String) =
            GistByUserFilterDialog(user, avatarUrl)
    }
}
