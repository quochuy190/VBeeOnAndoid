package com.vn.vbeeon.presentation.fragment.sphygmomanometer

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.lifecycle.ViewModelProviders
import com.ideabus.model.protocol.BaseProtocol
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.common.extensions.setTextHTML
import com.vn.vbeeon.domain.model.Global.sdkid_BT
import com.vn.vbeeon.presentation.activity.SphygmomanometerActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.fragment.user.ListUserFragment
import com.vn.vbeeon.presentation.viewmodel.SphygmomanometerViewModel
import com.vn.vbeeon.utils.AppUtils.currentTime
import com.vn.vbeeon.utils.AppUtils.getSecone
import kotlinx.android.synthetic.main.fragment_sphyg_home.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class SphygHomeFragment : BaseFragment(), BaseProtocol.OnConnectStateListener {
    //var protocol: BaseProtocol? = null
    lateinit var bpmProtocol: BaseProtocol
    lateinit var mainViewModel: SphygmomanometerViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initParam()
    }

    private fun initParam() {
        //Initialize the connection SDK
        bpmProtocol = BaseProtocol.getInstance(activity, false, true, sdkid_BT)

    }

    override fun onStart() {
        super.onStart()
        bpmProtocol.setOnConnectStateListener(this)
        // Global.protocol.setOnDataResponseListener(this)
        // Global.protocol.setOnNotifyStateListener(this)
        //  Global.protocol.setOnWriteStateListener(this)
        startScan()
    }

    override fun onStop() {
        super.onStop()
        Timber.e("onStop")

    }

    override fun onDestroy() {
        super.onDestroy()
        stop = true
        handler.removeCallbacksAndMessages(runnable);
        Timber.e("onDestroy")
//        bpmProtocol.stopScan(BaseProtocol.DeviceType.DEVICE_TYPE_ALL)
//        bpmProtocol.disconnect(BaseProtocol.DeviceType.DEVICE_TYPE_ALL)
//        bpmProtocol.disconnectBPM()

    }

    override fun onResume() {
        super.onResume()

    }


    private fun startScan() {
        if (!bpmProtocol.isSupportBluetooth(BaseProtocol.DeviceType.DEVICE_TYPE_ALL)) {
            return
        }
        bpmProtocol.startScan(10, BaseProtocol.DeviceType.DEVICE_TYPE_ALL)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_sphyg_home
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "HUYẾT ÁP"
        checkActivity()
        val sysTitle = "SYS<font color='#3497FD'><br>mmHg</font>"
        tv_sys_title.text = setTextHTML(sysTitle)
        val diaTitle = "DIA<font color='#3497FD'><br>mmHg</font>"
        tv_dia_title.text = setTextHTML(diaTitle)
        val pulseTitle = "PULSE/<font color='#3497FD'>min</font>"
        tv_pulse_title.text = setTextHTML(pulseTitle)
        imgUserHomeSphyg.setOnSafeClickListener {
            (context as SphygmomanometerActivity).openFragment(ListUserFragment(), true)
        }
    }

    override fun initViewModel() {
        mainViewModel =
            ViewModelProviders.of(activity as SphygmomanometerActivity, viewModelFactory).get(
                SphygmomanometerViewModel::class.java
            )
    }

    override fun observable() {

    }
    var stop = false
    lateinit var handler: Handler
    var  second=0
    var from =0
    fun checkActivity() {
        handler = Handler()
        handler.postDelayed(runnable, 0)
    }

    var runnable: Runnable = object : Runnable {
        override fun run() {
            Timber.e("log handler " + stop)
            if (!stop) {
                var second = getSecone()
                Timber.e("log handler " + second)
                tvHoursMinute.text = currentTime()
                val rotate = RotateAnimation(
                    (second*6).toFloat(), (second*6).toFloat(), Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                )
                rotate.duration = 1000*60
                rotate.interpolator = LinearInterpolator()
                viewTimeClock.startAnimation(rotate)
                handler.postDelayed(this, 1000)
            }
        }
    }


    override fun onScanResult(p0: String?, p1: String?, p2: Int, p3: BaseProtocol.DeviceType?) {
        Timber.e("log onScanResult" + p0 + p1 + p2 + p3)
    }

    override fun onScanResult(p0: BluetoothDevice?, p1: BaseProtocol.DeviceType?) {
        Timber.e("log onScanResult" + p0)
    }

    override fun onBtStateChanged(p0: Boolean) {
        Timber.e("log onBtStateChanged" + p0)
    }

    override fun onConnectionState(p0: BaseProtocol.ConnectState?, p1: BaseProtocol.DeviceType?) {
        Timber.e("log onConnectionState" + p0)
    }


}