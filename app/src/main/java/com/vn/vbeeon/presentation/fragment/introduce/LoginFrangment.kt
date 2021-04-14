package com.vn.vbeeon.presentation.fragment.introduce

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.VBeeOnApplication
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.RSACipher
import com.vn.vbeeon.common.extensions.RSACipher.stringToPublicKey
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.data.remote.entity.request.LoginRequest
import com.vn.vbeeon.extensions.enccriptData
import com.vn.vbeeon.presentation.activity.IntroduceActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber


@Suppress("DEPRECATION")
class LoginFrangment : BaseFragment() {

    lateinit var loginiewModel: LoginViewModel

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
        btnLogin.setOnSafeClickListener {
            Timber.e("login start")
            val file_name = "mobile_app.crt"
//            var publicKey = VBeeOnApplication.instance.assets.open(file_name).bufferedReader().use {
//                it.readText()
//            }
//            publicKey = publicKey.replace("-----BEGIN CERTIFICATE-----", "");
//            publicKey = publicKey.replace("-----END CERTIFICATE-----", "");
            loginiewModel.login(LoginRequest(edtUserName.text.toString(), enccriptData(edtPassWord.text.toString())))

        }
        txtForgotPass.setOnSafeClickListener {
            (context as IntroduceActivity).openFragment(ForgotPassFrangment(), true)
        }
        txtRegister.setOnSafeClickListener {
            (context as IntroduceActivity).openFragment(RegisterFrangment(), true)
        }
    }

    override fun initViewModel() {
        loginiewModel = ViewModelProviders.of(activity as IntroduceActivity, viewModelFactory).get(
            LoginViewModel::class.java)

    }

    override fun observable() {
        loginiewModel.getLoading().observeForever(::showProgressDialog)
        loginiewModel.response.observe(this,  Observer {
            Timber.e("success")
           // (context as IntroduceActivity).openFragment(LoginFrangment(), false)
        })
        loginiewModel.error.observe(this, Observer {
            //Timber.e("error"+it.message)
        })
    }


}