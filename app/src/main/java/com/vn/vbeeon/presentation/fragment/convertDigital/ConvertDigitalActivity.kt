package com.vn.vbeeon.presentation.fragment.convertDigital

import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.vn.vbeeon.R
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.presentation.base.BaseActivity

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-December-2020
 * Time: 23:38
 * Version: 1.0
 */
class ConvertDigitalActivity : BaseActivity() {

    override fun provideLayoutId() = R.layout.activity_convert_digital

    override fun setupView(savedInstanceState: Bundle?) {
        openFragment(ConvertDigitalDetailFragment(), false)
        checkActivity()

    }
    var handler: Handler? = null
    fun checkActivity() {
        handler = Handler()
        handler!!.postDelayed(runnable, 0)
    }

    var runnable: Runnable = object : Runnable {
        override fun run() {
            val rotate = RotateAnimation(
                0F, 360F, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f
            )
            rotate.duration = 1000 * 60.toLong()
            rotate.interpolator = LinearInterpolator()
            //image.startAnimation(rotate)
            handler!!.postDelayed(this, 1000 * 60.toLong())
        }
    }
}