package com.vn.vbeeon.presentation.fragment.convertDigital

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.ConvertDigitalActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_convert_digital.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class ConvertDigitalPageFragment : BaseFragment() {
    lateinit var mainViewModel: MainViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_convert_digital
    }

    override fun initView() {
        itemBaseDigital.setOnClickListener {
            startActivityOption(0)
        }
        itemSmartDigital.setOnClickListener {
            startActivityOption(1)
        }
        itemMissionDigital.setOnClickListener {
            startActivityOption(2)
        }
        imgCSKH.setOnSafeClickListener {
            checkPermission()
        }
    }

    private fun startActivityOption(option : Int) {
        context?.launchActivity<ConvertDigitalActivity>{
            putExtra(ConstantCommon.KEY_SEND_OPTION_CD, option)
        }
    }

    override fun initViewModel() {
//        mainViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(
//            MainViewModel::class.java)
    }

    override fun observable() {

    }

    fun checkPermission() {
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.CALL_PHONE)
            }
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0823830506"))
        startActivity(intent)
    }
}