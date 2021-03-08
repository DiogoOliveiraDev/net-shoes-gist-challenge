package com.example.netshoesgistchallenge.features.gistdetails.view

import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.common.extensions.bind
import com.example.netshoesgistchallenge.common.extensions.loadImage
import com.example.netshoesgistchallenge.common.extensions.setVisibility
import com.example.netshoesgistchallenge.features.gistdetails.state.GistDetailsState
import com.example.netshoesgistchallenge.features.gistdetails.viewmodel.GistDetailsViewModel
import com.example.netshoesgistchallenge.global.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import thereisnospon.codeview.CodeView
import thereisnospon.codeview.CodeViewTheme

class GistDetailsActivity : BaseActivity() {

    private val cvContent by lazy { bind<CodeView>(R.id.cvContent) }
    private val ivAvatar  by lazy { bind<ImageView>(R.id.ivAvatar) }
    private val tvUser  by lazy { bind<TextView>(R.id.tvUser)  }
    private val tvLanguage  by lazy { bind<TextView>(R.id.tvLanguage) }
    private val viewModel by viewModel<GistDetailsViewModel>()
    private val flError by lazy { bind<FrameLayout>(R.id.flError)}
    private val tvErrorMessage by lazy { bind<TextView>(R.id.tvErrorMessage)}

    override fun getLayout() = R.layout.activity_gist_details

    override fun initialize() {
        viewModel.gistDetailsStream.observe(this) {
            onStateUpdate(it)
        }

        fromBundle<String>(GIST_ID_KEY) {
            viewModel.getFileContentById(it)
        }

        supportActionBar?.title = resources.getString(R.string.status_bar_favorites_content_name)
    }

    private fun onStateUpdate(state: GistDetailsState) {
        cvContent.setTheme(CodeViewTheme.MONOKAI).fillColor();
        cvContent.showCode(state.content)
        ivAvatar.loadImage(this, state.avatarUrl)
        tvUser.text = state.user
        tvLanguage.text = state.language
        flError.setVisibility(state.contentErrorVisibility)
        tvErrorMessage.text = resources.getString(state.errorMessageResource)
    }

    companion object{
        const val GIST_ID_KEY = "GIST_ID_KEY"
    }
}
