package com.vn.vbeeon.presentation.fragment.sphygmomanometer

import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.setTextHTML
import com.vn.vbeeon.presentation.activity.SphygmomanometerActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.SphygmomanometerViewModel
import kotlinx.android.synthetic.main.fragment_sphyg_home.*


@Suppress("DEPRECATION")
class SphygHomeFragment : BaseFragment() {

    lateinit var mainViewModel: SphygmomanometerViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_sphyg_home
    }

    override fun initView() {
        checkActivity()
        val sysTitle = "SYS<font color='#3497FD'><br>mmHg</font>"
        tv_sys_title.text  = setTextHTML(sysTitle)
        val diaTitle = "DIA<font color='#3497FD'><br>mmHg</font>"
        tv_dia_title.text  = setTextHTML(diaTitle)
        val pulseTitle = "PULSE/<font color='#3497FD'>min</font>"
        tv_pulse_title.text  = setTextHTML(pulseTitle)
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as SphygmomanometerActivity, viewModelFactory).get(
            SphygmomanometerViewModel::class.java)
    }

    override fun observable() {

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
            viewTimeClock.startAnimation(rotate)
            handler!!.postDelayed(this, 1000 * 60.toLong())
        }
    }

}