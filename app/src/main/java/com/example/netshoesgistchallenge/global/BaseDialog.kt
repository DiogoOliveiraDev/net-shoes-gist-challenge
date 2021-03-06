package com.example.netshoesgistchallenge.global

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

abstract class BaseDialog(
    private val widthPercent: Int = DEFAULT_DIALOG_WIDTH,
    private val heightPercent: Int = DEFAULT_DIALOG_HEIGHT
) : DialogFragment() {

    companion object{
        const val DEFAULT_DIALOG_WIDTH = 90
        const val DEFAULT_DIALOG_HEIGHT = 60
        const val FULL_SCREEN_PERCENT = 100
        const val NO_VALUE = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(getLayout(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(widthPercent, heightPercent)
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun initialize()

    private fun setWidthPercent(widthPercent: Int, heightPercent: Int) {
         val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(NO_VALUE, NO_VALUE, widthPixels, heightPixels) }
        val percentWidth = rect.width() * widthPercent.toFloat() / FULL_SCREEN_PERCENT
        val percentHeight = rect.height() * heightPercent.toFloat() / FULL_SCREEN_PERCENT
        dialog?.window?.setLayout(percentWidth.toInt(), percentHeight.toInt())
    }
}
