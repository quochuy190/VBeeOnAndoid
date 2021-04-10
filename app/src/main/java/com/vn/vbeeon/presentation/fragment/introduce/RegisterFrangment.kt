package com.vn.vbeeon.presentation.fragment.introduce

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.data.remote.entity.request.RegisterRequest
import com.vn.vbeeon.presentation.activity.IntroduceActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_register.*
import timber.log.Timber


@Suppress("DEPRECATION")
class RegisterFrangment : BaseFragment() {
    // test commit
    lateinit var mainViewModel: LoginViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_register
    }

    override fun initView() {

        btn_register.setOnSafeClickListener {
            var request: RegisterRequest = RegisterRequest("",
                edtUserName.text.toString(),
                edtPass.text.toString(),
                "", "", "",
                edtPhone.text.toString(), "",
                edtEmail.text.toString(), "")
            mainViewModel.register(request)
        }
        txtLogin.setOnSafeClickListener {

           // (context as IntroduceActivity).openFragment(LoginFrangment(), false)
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as IntroduceActivity, viewModelFactory).get(
            LoginViewModel::class.java
        )
    }

    override fun observable() {
        mainViewModel.getLoading().observeForever(::showProgressDialog)
        mainViewModel.response.observe(this,  Observer {
            Timber.e("success")
            (context as IntroduceActivity).openFragment(LoginFrangment(), false)
        })
        mainViewModel.error.observe(this, Observer {
            Timber.e("error"+it.message)
        })
    }


}