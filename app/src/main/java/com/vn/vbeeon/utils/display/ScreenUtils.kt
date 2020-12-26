package com.vsm.ambientmode.utils.display

import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation


object ScreenUtils {

    fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

    fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels

    fun slideUp(view: View) {
        val slideUp = TranslateAnimation(0f, 0f,0f, -view.height.toFloat()).apply {
            duration = 300
            fillAfter = true
        }
        view.startAnimation(slideUp)
    }

    fun slideDown(view: View) {
        val slideDown = TranslateAnimation(0f, 0f, -view.height.toFloat(), 0f).apply {
            duration = 300
            fillAfter = true
        }
        view.startAnimation(slideDown)
    }
}