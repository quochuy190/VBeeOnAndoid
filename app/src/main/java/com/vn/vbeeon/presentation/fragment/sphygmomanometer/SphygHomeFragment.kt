package com.vn.vbeeon.presentation.fragment.sphygmomanometer

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.lifecycle.ViewModelProviders
import com.ideabus.model.data.*
import com.ideabus.model.protocol.BaseProtocol
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.setTextHTML
import com.vn.vbeeon.domain.model.Global
import com.vn.vbeeon.domain.model.Global.sdkid_BPM
import com.vn.vbeeon.presentation.activity.SphygmomanometerActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.SphygmomanometerViewModel
import kotlinx.android.synthetic.main.fragment_sphyg_home.*
import timber.log.Timber


@Suppress("DEPRECATION")
class SphygHomeFragment : BaseFragment(), BaseProtocol.OnConnectStateListener,
    BaseProtocol.OnDataResponseListener{

    lateinit var mainViewModel: SphygmomanometerViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun initParam() {

        //Initialize the connection SDK
        Global.protocol = BaseProtocol.getInstance(context as Activity?, false, true, sdkid_BPM)
        Global.protocol.setOnConnectStateListener(this)
        Global.protocol.setOnDataResponseListener(this)
       // Global.protocol.setOnNotifyStateListener(this)
      //  Global.protocol.setOnWriteStateListener(this)
    }
    private fun startScan() {
        if (!Global.protocol.isSupportBluetooth(BaseProtocol.DeviceType.DEVICE_TYPE_ALL)) {
            return
        }
        Global.protocol.startScan(10, BaseProtocol.DeviceType.DEVICE_TYPE_ALL)
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

    override fun onScanResult(mac: String?, name: String?, rssi: Int, deviceType: BaseProtocol.DeviceType?) {

        //When scanning BLE, it will be sent back, and then added to the list.
//        Log.d(
//            com.ideabus.sdk_test.ConnectionActivity.TAG,
//            "onScanResult name = $name"
//        )
        Timber.d("onScanResult name = $name")

        if (deviceType == BaseProtocol.DeviceType.DEVICE_TYPE_THERMO) {
            Global.protocol.stopScan(BaseProtocol.DeviceType.DEVICE_TYPE_THERMO)
            //Connection
            Global.protocol.connect(mac, BaseProtocol.DeviceType.DEVICE_TYPE_THERMO)
        }

        if (deviceType == BaseProtocol.DeviceType.DEVICE_TYPE_BPM) {
            //Stop scanning before connecting
            Global.protocol.stopScan(BaseProtocol.DeviceType.DEVICE_TYPE_BPM)
            //Connection
            if (name!!.startsWith("A")) {
                Global.protocol.connect(mac, BaseProtocol.DeviceType.DEVICE_TYPE_BPM)
            } else {
                Global.protocol.bond(mac, BaseProtocol.DeviceType.DEVICE_TYPE_BPM)
            }
        }
    }

    override fun onScanResult(p0: BluetoothDevice?, p1: BaseProtocol.DeviceType?) {
        TODO("Not yet implemented")
    }

    override fun onBtStateChanged(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onConnectionState(p0: BaseProtocol.ConnectState?, p1: BaseProtocol.DeviceType?) {
        TODO("Not yet implemented")
    }

    override fun onResponseClearLastData(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onResponseReadHistory(p0: DRecord?) {
        TODO("Not yet implemented")
    }

    override fun onDeleteAllUsersSuccess() {
        TODO("Not yet implemented")
    }

    override fun onResponseDeviceInfo(p0: String?, p1: Int, p2: Float) {
        TODO("Not yet implemented")
    }

    override fun onResponseWriteUser(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onResponseReadLastData(
        p0: CurrentAndMData?,
        p1: Int,
        p2: Int,
        p3: Int,
        p4: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun onResponseReadDeviceTime(p0: DeviceInfo?) {
        TODO("Not yet implemented")
    }

    override fun onResponseReadUserAndVersionData(p0: User?, p1: VersionData?) {
        TODO("Not yet implemented")
    }

    override fun onResponseClearHistory(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onResponseReadDeviceInfo(p0: DeviceInfo?) {
        TODO("Not yet implemented")
    }

    override fun onResponseUploadMeasureData(p0: ThermoMeasureData?) {
        TODO("Not yet implemented")
    }

    override fun onResponseWriteDeviceTime(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onUserInfoUpdateSuccess() {
        TODO("Not yet implemented")
    }

    override fun onResponseMeasureResult2(p0: EBodyMeasureData?, p1: Float) {
        TODO("Not yet implemented")
    }


}