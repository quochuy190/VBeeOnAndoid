package com.vn.vbeeon.presentation.activity

import android.os.Bundle
import com.vn.vbeeon.R
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.presentation.base.BaseActivity
import com.vn.vbeeon.presentation.fragment.sphygmomanometer.SphygHomeFragment

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-December-2020
 * Time: 23:38
 * Version: 1.0
 */
class SphygmomanometerActivity : BaseActivity() {

    override fun provideLayoutId() = R.layout.activity_convert_digital

    override fun setupView(savedInstanceState: Bundle?) {
        openFragment(SphygHomeFragment(), false)
    }
}