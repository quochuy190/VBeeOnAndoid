package com.vn.vbeeon.presentation.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.ButterKnife
import butterknife.Unbinder
import com.vn.vbeeon.VBeeOnApplication
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.presentation.viewmodel.ViewModelFactory
import com.vsm.ambientmode.utils.display.Toaster

import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var unBinder: Unbinder

    abstract fun inject(appComponent: AppComponent)
    abstract fun getLayoutRes(): Int
    abstract fun initView()
    abstract fun initViewModel()
    abstract fun observable()
    private var progressDialog: ProgressDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject(VBeeOnApplication.instance.appComponent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutRes(), container, false)
        unBinder = ButterKnife.bind(this, view)
        progressDialog = ProgressDialog(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        observable()
    }

    override fun onDestroyView() {
        unBinder.unbind()
        super.onDestroyView()
    }

    fun showProgressDialog(isShow: Boolean) {
        if (isShow) {
            showProgress()
        } else {
            dismissProgress()
        }
    }
    fun showProgress() {
        if (!progressDialog!!.isShowing()) progressDialog!!.show()
    }

    fun dismissProgress() {
        if (progressDialog!!.isShowing()) progressDialog!!.dismiss()
    }
    fun showMessage(message: String) = context?.let { Toaster.show(it, message) }

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    protected fun handleStatusRequest(@Status status: Int?, swipeRefreshLayout: SwipeRefreshLayout? = null) {
        when (status) {
            BaseViewModel.RQ_START -> {
                showProgressDialog(true)
            }
            BaseViewModel.RQ_FINISH -> {
                swipeRefreshLayout?.isRefreshing = false
                showProgressDialog(false)
            }
        }
    }

    protected fun handleError(throwable: Throwable?) {
        Timber.e(throwable)
    }

}