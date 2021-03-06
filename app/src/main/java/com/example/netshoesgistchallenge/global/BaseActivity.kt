package com.example.netshoesgistchallenge.global

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.netshoesgistchallenge.features.gistdetails.view.GistDetailsActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        initialize()
    }

    protected fun <T>fromBundle(key: String, callback: (T)-> Unit) {
        intent.extras?.getString(key).apply {
            if (this != null) {
                callback.invoke(this as T)
            }
        }
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun initialize()
}
