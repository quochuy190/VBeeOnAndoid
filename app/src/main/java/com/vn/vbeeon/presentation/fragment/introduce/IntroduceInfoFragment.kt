package com.vn.vbeeon.presentation.fragment.introduce

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.IntroduceActivity
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import com.vn.vbeeon.utils.SharedPrefs
import kotlinx.android.synthetic.main.fragment_input_info.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class IntroduceInfoFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_input_info
    }

    override fun initView() {
        btnContinue.setOnSafeClickListener {
            if (validateEdt()){
                SharedPrefs.instance.put(ConstantCommon.IS_FIRST_OPEN_APP, true)
                SharedPrefs.instance.put(ConstantCommon.USER_FULL_NAME, edtFullName.text.toString())
                (context as IntroduceActivity).launchActivity<MainActivity> {  }
            }
        }
    }

    private fun validateEdt() : Boolean{
        if (edtFullName.text.toString().length==0){
            edtFullName.requestFocus()
            edtFullName.setSelection(0)
            showMessage(R.string.emtry_input_name)
            return false
        }
        return true
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as IntroduceActivity, viewModelFactory).get(
            MainViewModel::class.java)
    }

    override fun observable() {

    }


}