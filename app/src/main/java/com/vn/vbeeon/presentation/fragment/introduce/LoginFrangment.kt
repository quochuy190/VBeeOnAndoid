package com.vn.vbeeon.presentation.fragment.introduce

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.IntroduceActivity
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_login.*


@Suppress("DEPRECATION")
class LoginFrangment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        txtForgotPass.setOnSafeClickListener {
            (context as IntroduceActivity).openFragment(ForgotPassFrangment(), true)
        }
        txtRegister.setOnSafeClickListener {
            (context as IntroduceActivity).openFragment(RegisterFrangment(), true)
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as IntroduceActivity, viewModelFactory).get(
            MainViewModel::class.java)
    }

    override fun observable() {

    }


}