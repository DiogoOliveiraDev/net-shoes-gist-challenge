package com.example.netshoesgistchallenge.features.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.common.bind
import com.example.netshoesgistchallenge.common.invertVisibility
import com.example.netshoesgistchallenge.common.loadImage
import com.example.netshoesgistchallenge.common.setVisibility
import com.example.netshoesgistchallenge.features.home.adapter.listeners.AvatarClickListener
import com.example.netshoesgistchallenge.features.home.adapter.listeners.GistItemClickListener
import com.example.netshoesgistchallenge.features.home.adapter.listeners.ManageFavoriteListener
import com.example.netshoesgistchallenge.global.BaseRecyclerViewAdapter

class GistListAdapter(
    private val listener: GistItemClickListener,
    private val manageFavoriteListener: ManageFavoriteListener,
    private val avatarClickListener: AvatarClickListener
) : BaseRecyclerViewAdapter<GistItem, GistListAdapter.ViewHolder>() {

    override fun getLayout() = R.layout.layout_gist_element

    override fun getViewHolder(view: View) = ViewHolder(view, this)

    class ViewHolder(
            view: View,
            private val adapter: GistListAdapter
    ) : BaseRecyclerViewAdapter.BaseViewHolder<GistItem>(view) {

        private val ivAvatar = bind<ImageView>(R.id.ivAvatar)
        private val tvUser = bind<TextView>(R.id.tvUser)
        private val tvType = bind<TextView>(R.id.tvType)
        private val clContent = bind<ConstraintLayout>(R.id.clContent)
        private val tvLanguage = bind<TextView>(R.id.tvFileName)
        private val vFavorite = bind<View>(R.id.vFavorite)
        private val ivFavoriteStatus = bind<ImageView>(R.id.ivFavoriteStatus)
        private val tvFileName = bind<TextView>(R.id.tvFileName)

        override fun bind(item: GistItem) {
            setClickItemListener(item)
            manageFavorite(item)
            updateTexts(item)
            loadImage(item)
        }

        private fun manageFavorite(item: GistItem) {
            ivFavoriteStatus.setVisibility(item.isFavorite)

            vFavorite.setOnClickListener{
                ivFavoriteStatus.invertVisibility()

                adapter.manageFavoriteListener.onChangeFavoriteStatus(
                        item.gistId, ivFavoriteStatus.isVisible
                )
            }
        }

        private fun setClickItemListener(item: GistItem) {
            clContent.setOnClickListener{
                adapter.listener.onItemClick(
                    gistId = item.gistId,
                    avatarUrl = item.avatarUrl,
                    user = item.user,
                    language = item.language
                )
            }

            ivAvatar.setOnClickListener {
                adapter.avatarClickListener.onClickAvatar(item.user, item.avatarUrl)
            }
        }

        private fun updateTexts(item: GistItem) {
            tvUser.text = item.user
            tvType.text = item.contentType
            tvLanguage.text = item.language
            tvFileName.text = item.fileTitle
        }

        private fun loadImage(item: GistItem) {
            ivAvatar.loadImage(
                    itemView.context,
                    item.avatarUrl
            )
        }
    }
}
