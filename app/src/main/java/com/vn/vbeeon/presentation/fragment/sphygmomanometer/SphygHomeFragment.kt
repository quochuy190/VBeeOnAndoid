package com.vn.vbeeon.presentation.fragment.sphygmomanometer

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.lifecycle.ViewModelProviders
import com.ideabus.model.protocol.BPMProtocol
import com.ideabus.model.protocol.BaseProtocol
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.common.extensions.setTextHTML
import com.vn.vbeeon.domain.model.Global
import com.vn.vbeeon.domain.model.Global.sdkid_BPM
import com.vn.vbeeon.presentation.activity.SphygmomanometerActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.fragment.user.ListUserFragment
import com.vn.vbeeon.presentation.viewmodel.SphygmomanometerViewModel
import kotlinx.android.synthetic.main.fragment_sphyg_home.*


@Suppress("DEPRECATION")
class SphygHomeFragment : BaseFragment(), BPMProtocol.OnConnectStateListener {
    //var protocol: BaseProtocol? = null
    lateinit var bpmProtocol: BPMProtocol
    lateinit var mainViewModel: SphygmomanometerViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initParam()
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
        Global.bpmProtocol.disconnect()
        Global.bpmProtocol.stopScan()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (Global.bpmProtocol.isConnected) Global.bpmProtocol.disconnect()
        Global.bpmProtocol.stopScan()
    }
    override fun onResume() {
        super.onResume()

    }

    private fun initParam() {
        //Initialize the connection SDK
        bpmProtocol = BPMProtocol.getInstance(context as SphygmomanometerActivity, false, true, sdkid_BPM)

    }

    private fun startScan() {
        if (bpmProtocol.isSupportBluetooth(context as SphygmomanometerActivity)) {

            return
        }
        bpmProtocol.startScan(10)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_sphyg_home
    }

    override fun initView() {
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


    override fun onScanResult(p0: String?, p1: String?, p2: Int) {

    }

    override fun onBtStateChanged(p0: Boolean) {

    }

    override fun onConnectionState(p0: BPMProtocol.ConnectState?) {
    }

//
//    override fun onScanResult(
//        mac: String?,
//        name: String?,
//        rssi: Int,
//        deviceType: BaseProtocol.DeviceType?
//    ) {
//        Timber.d("onScanResult name = $name")
//
//        if (deviceType == BaseProtocol.DeviceType.DEVICE_TYPE_THERMO) {
//            Global.protocol.stopScan(BaseProtocol.DeviceType.DEVICE_TYPE_THERMO)
//            //Connection
//            Global.protocol.connect(mac, BaseProtocol.DeviceType.DEVICE_TYPE_THERMO)
//        }
//
//        if (deviceType == BaseProtocol.DeviceType.DEVICE_TYPE_BPM) {
//            //Stop scanning before connecting
//            Global.protocol.stopScan(BaseProtocol.DeviceType.DEVICE_TYPE_BPM)
//            //Connection
//            if (name!!.startsWith("A")) {
//                Global.protocol.connect(mac, BaseProtocol.DeviceType.DEVICE_TYPE_BPM)
//            } else {
//                Global.protocol.bond(mac, BaseProtocol.DeviceType.DEVICE_TYPE_BPM)
//            }
//        }
//    }
//
//    override fun onScanResult(device: BluetoothDevice?, deviceType: BaseProtocol.DeviceType?) {
////When scanning BLE, it will be sent back, and then added to the list.
//        Global.protocol.stopScan(BaseProtocol.DeviceType.DEVICE_TYPE_EBODY)
//        //Connection
//        //Connection
//        Global.protocol.connect(device, BaseProtocol.DeviceType.DEVICE_TYPE_EBODY)
//    }
//
//    override fun onBtStateChanged(isEnable: Boolean) {
//        if (isEnable) {
//            startScan()
//        } else {
//
//        }
//    }
//
//    override fun onConnectionState(state: BaseProtocol.ConnectState?, deviceType: BaseProtocol.DeviceType?) {
//        when (state) {
//            BaseProtocol.ConnectState.Connected -> Timber.d("connect")
//
//            BaseProtocol.ConnectState.ConnectTimeout, BaseProtocol.ConnectState.Disconnect -> {
//                //statusText.setText(deviceType.toString() + "：斷線")
//                startScan()
//            }
//            BaseProtocol.ConnectState.ScanFinish -> {
//                //statusText.setText(deviceType.toString() + "：結束")
//                startScan()
//            }
//            BaseProtocol.ConnectState.ScaleSleep -> {
//               // statusText.setText(deviceType.toString() + "：睡眠")
//                startScan()
//            }
//            BaseProtocol.ConnectState.ScaleWake -> {
//               // statusText.setText(deviceType.toString() + "：啟動")
//                //sendUserInfoToScale()
//            }
//        }
//    }
//
//    override fun onResponseClearLastData(p0: Boolean) {
//
//    }
//
//    override fun onResponseReadHistory(p0: DRecord?) {
//
//    }
//
//    override fun onDeleteAllUsersSuccess() {
//
//    }
//
//    override fun onResponseDeviceInfo(p0: String?, p1: Int, p2: Float) {
//
//    }
//
//    override fun onResponseWriteUser(p0: Boolean) {
//
//    }
//
//    override fun onResponseReadLastData(
//        p0: CurrentAndMData?,
//        p1: Int,
//        p2: Int,
//        p3: Int,
//        p4: Boolean
//    ) {
//
//    }
//
//    override fun onResponseReadDeviceTime(p0: DeviceInfo?) {
//
//    }
//
//    override fun onResponseReadUserAndVersionData(p0: User?, p1: VersionData?) {
//
//    }
//
//    override fun onResponseClearHistory(p0: Boolean) {
//
//    }
//
//    override fun onResponseReadDeviceInfo(p0: DeviceInfo?) {
//
//    }
//
//    override fun onResponseUploadMeasureData(p0: ThermoMeasureData?) {
//
//    }
//
//    override fun onResponseWriteDeviceTime(p0: Boolean) {
//
//    }
//
//    override fun onUserInfoUpdateSuccess() {
//
//    }
//
//    override fun onResponseMeasureResult2(p0: EBodyMeasureData?, p1: Float) {
//
//    }


}