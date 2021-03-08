package com.example.netshoesgistchallenge.features.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.netshoesgistchallenge.R
import com.example.netshoesgistchallenge.common.extensions.hasPrefs
import com.example.netshoesgistchallenge.features.home.view.activity.HomeActivity
import com.example.netshoesgistchallenge.features.tutorial.view.activity.TutorialActivity
import com.example.netshoesgistchallenge.features.tutorial.view.activity.TutorialActivity.Companion.ALREADY_VISUALIZED_TUTORIAL_KEY
import com.example.netshoesgistchallenge.global.BaseActivity

class SplashActivity : BaseActivity() {

    companion object {
        const val DELAY: Long = 1500
    }

    override fun getLayout() = R.layout.actitivty_splash

    override fun initialize() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (hasPrefs(ALREADY_VISUALIZED_TUTORIAL_KEY)) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, TutorialActivity::class.java))
            }

            finish()
        }, DELAY)

    }
}
