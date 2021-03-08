package com.example.netshoesgistchallenge.features.tutorial.view.activity

import android.content.Intent
import android.widget.FrameLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.common.extensions.bind
import com.example.netshoesgistchallenge.common.extensions.hasPrefs
import com.example.netshoesgistchallenge.common.extensions.putPrefsBool
import com.example.netshoesgistchallenge.common.extensions.setVisibility
import com.example.netshoesgistchallenge.features.home.view.activity.HomeActivity
import com.example.netshoesgistchallenge.features.tutorial.state.TutorialState
import com.example.netshoesgistchallenge.features.tutorial.view.fragment.TutorialStep1Fragment
import com.example.netshoesgistchallenge.features.tutorial.view.fragment.TutorialStep2Fragment
import com.example.netshoesgistchallenge.features.tutorial.view.fragment.TutorialStep3Fragment
import com.example.netshoesgistchallenge.features.tutorial.view.fragment.TutorialStep4Fragment
import com.example.netshoesgistchallenge.features.tutorial.viewmodel.TutorialViewModel
import com.example.netshoesgistchallenge.global.BaseActivity
import com.example.netshoesgistchallenge.global.ViewPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TutorialActivity : BaseActivity(){

    companion object {
        const val ALREADY_VISUALIZED_TUTORIAL_KEY = "ALREADY_VISUALIZED_TUTORIAL_KEY"
    }

    private val viewModel: TutorialViewModel by viewModel()

    private val vpContent by lazy { bind<ViewPager2>(R.id.vpContent) }
    private val tvDescription by lazy { bind<TextView>(R.id.tvDescription) }
    private val tvClose by lazy { bind<TextView>(R.id.tvClose) }
    private val flBack by lazy { bind<FrameLayout>(R.id.flBack) }
    private val flNext by lazy { bind<FrameLayout>(R.id.flNext) }

    override fun getLayout() = R.layout.actitivty_tutorial

    override fun initialize() {
        viewModel.stateStream.observe(this){
            onStateUpdate(it)
        }

        viewModel.createInitialState()

        initializeView()
    }

    private fun initializeView() {
        vpContent.adapter = ViewPagerAdapter.createPages(
            this,
            listOf(
                TutorialStep1Fragment(),
                TutorialStep2Fragment(),
                TutorialStep3Fragment(),
                TutorialStep4Fragment(),
            )
        )

        vpContent.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                viewModel.onPageChange(position)
            }
        })

        tvClose.setOnClickListener{
            saveVisualizedTutorial()
        }

        supportActionBar?.title = resources.getString(R.string.tutorial_menu_title)

        flBack.setOnClickListener{
            viewModel.onClickBack(vpContent.currentItem)
        }

        flNext.setOnClickListener{
            viewModel.onClickNext(vpContent.currentItem)
        }
    }

    private fun onStateUpdate(state: TutorialState) {
        tvDescription.text = resources.getString(state.tvDescriptionResourceId)
        tvClose.setVisibility(state.tvCloseVisibility)
        flBack.setVisibility(state.backButtonVisibility)
        flNext.setVisibility(state.nextButtonVisibility)
        vpContent.setCurrentItem(state.pagerPosition, true)
    }

    override fun onBackPressed() {
        saveVisualizedTutorial()
    }

    fun saveVisualizedTutorial() {
        if (hasPrefs(ALREADY_VISUALIZED_TUTORIAL_KEY)) {
            finish()
        } else {
            putPrefsBool(ALREADY_VISUALIZED_TUTORIAL_KEY, true)
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
