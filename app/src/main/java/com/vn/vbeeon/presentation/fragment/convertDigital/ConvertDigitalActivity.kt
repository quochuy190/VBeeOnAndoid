package com.vn.vbeeon.presentation.fragment.convertDigital

import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.vn.vbeeon.R
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.presentation.base.BaseActivity
import timber.log.Timber

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
        val option  = intent.getIntExtra("Option", 0)
        Timber.d("option $option")
        when(option){
            0 -> openFragment(ConvertDigitalDetailFragment(), false)
            1 -> openFragment(DemoVBeeOnMission(), false)
            2 -> openFragment(DemoVBeeOnMission(), false)
        }

    }
}