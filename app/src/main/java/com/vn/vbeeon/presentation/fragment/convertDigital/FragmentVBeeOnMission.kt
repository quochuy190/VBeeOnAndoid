package com.vn.vbeeon.presentation.fragment.convertDigital

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.common.extensions.setTextHTML
import com.vn.vbeeon.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_vbeeon_mission.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class FragmentVBeeOnMission : BaseFragment() {

   // lateinit var mainViewModel: ConvertDigitalViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_vbeeon_mission
    }

    override fun initView() {
        Timber.e("FragmentVBeeOnMission")
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "SỨ MỆNH CỦA VBEEON"
        val txtMission = "<font color='#FFB900'>SỨ MỆNH CỦA VBEEON</font><br><br>" +
                "KIẾN TẠO <font color='#00FFFF'>NỀN TẢNG THÔNG TIN</font> TỪ KẾT NỐI " +
                "<font color='#FFB900'>IoT/IIoT (INTERNET VẠN VẬT KẾT NỐI/ IoT CÔNG NHIỆP)</font>, " +
                "BIẾN KHỐI THÔNG TIN ĐÓ TRỞ THÀNH HỮU ÍCH VÀ MANG LẠI CÁC " +
                "<font color='#00FFFF'>ỨNG DỤNG THÔNG MINH</font> QUA " +
                "<font color='#FFB900'>AI (TRÍ TUỆ NHÂN TẠO)</font> CHO MỌI LĨNH VỰC CỦA CUỘC SỐNG"
        tvMissionItForLife.text  = setTextHTML(txtMission)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvMissionItForLife.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
//        val txtCompany = "<font color='#3497FD'>Bee Innovative</font> và <font color='#3497FD'>AHG</font>"
//        tvCompany.text  = setTextHTML(txtCompany)
//        tvCompany.visibility = View.GONE
//        tvTitleCompany.visibility = View.GONE
//        tvTitleWebsite.visibility = View.GONE
//        tvWebsite.visibility = View.GONE
    }

    override fun initViewModel() {
//        mainViewModel = ViewModelProviders.of(activity as ConvertDigitalActivity, viewModelFactory).get(
//            ConvertDigitalViewModel::class.java)
    }

    override fun observable() {

    }


}