package com.vn.vbeeon.presentation.fragment.sphygmomanometer

import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
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
    private var myBluetooth: BluetoothAdapter? = null
    val Request_Enable_Blutooth=1
    val mac : String = "0000fff1-0000-1000-8000-00805f9b34fb"
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }
    var BTAdapter = BluetoothAdapter.getDefaultAdapter()
    var REQUEST_BLUETOOTH = 1005
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (BTAdapter == null) {
            AlertDialog.Builder(activity)
                .setTitle("Not compatible")
                .setMessage("Your phone does not support Bluetooth")
                .setPositiveButton("Exit",
                    DialogInterface.OnClickListener { dialog, which -> System.exit(0) })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
        if (!BTAdapter.isEnabled) {
            val enableBT = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBT, REQUEST_BLUETOOTH)
        }
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        activity?.registerReceiver(bReciever, filter);
        BTAdapter.startDiscovery();
        Timber.e("startDiscovery ")
        initParam()
    }

    private fun initParam() {
        //Initialize the connection SDK
        myBluetooth = BluetoothAdapter.getDefaultAdapter()
        if (myBluetooth == null) {
        }
        if (!myBluetooth!!.isEnabled) {
            val enableBlutoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBlutoothIntent, Request_Enable_Blutooth)
        }
        bpmProtocol = BaseProtocol.getInstance(activity, false, true, sdkid_BT)

    }

    private val bReciever: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                // Create a new device item
//                val newDevice =
//                    DeviceItem(device!!.name, device!!.address, "false")
//                // Add it to our adapter
//                mAdapter.add(newDevice)
                Timber.e(""+device!!.name+", "+device!!.address)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bpmProtocol.setOnConnectStateListener(this)
        // Global.protocol.setOnDataResponseListener(this)
        // Global.protocol.setOnNotifyStateListener(this)
        //  Global.protocol.setOnWriteStateListener(this)
      //  startScan()
    }

    override fun onStop() {
        super.onStop()
        Timber.e("onStop")
        getActivity()?.unregisterReceiver(bReciever);
        BTAdapter.cancelDiscovery();
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
        if (!bpmProtocol.isSupportBluetooth(BaseProtocol.DeviceType.DEVICE_TYPE_BPM)) {
            return
        }
        bpmProtocol.startScan(5, BaseProtocol.DeviceType.DEVICE_TYPE_BPM)
        //bpmProtocol.connect(mac, BaseProtocol.DeviceType.DEVICE_TYPE_BPM)
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
    var second = 0
    var from = 0
    fun checkActivity() {
        handler = Handler()
        handler.postDelayed(runnable, 0)
    }

    var runnable: Runnable = object : Runnable {
        override fun run() {
            if (!stop) {
                var second = getSecone()
                tvHoursMinute.text = currentTime()
                val rotate = RotateAnimation(
                    (second * 6).toFloat(), (second * 6).toFloat(), Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                )
                rotate.duration = 1000 * 60
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