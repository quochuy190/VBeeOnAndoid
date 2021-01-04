package com.vn.vbeeon.presentation.fragment.convertDigital

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vsm.ambientmode.ui.timer.HtmlAdapter
import kotlinx.android.synthetic.main.fragment_webv.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber

@Suppress("DEPRECATION")
class WebViewFragment : BaseFragment() {
    lateinit var mViewModel: FragmentListWebHtmlViewModel
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    companion object {
        fun newInstance(jsonFile: String): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString("content", jsonFile)
            fragment.setArguments(args)
            return fragment
        }
    }

    var content: String = "";
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("content")?.let {
            content = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_webv
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        wvConvertDigital.getSettings().setJavaScriptEnabled(true);
        wvConvertDigital.loadData(content, "text/html; charset=utf-8", "UTF-8");
    }

    override fun initViewModel() {
        mViewModel =
            ViewModelProviders.of(activity as ConvertDigitalActivity, viewModelFactory).get(
                FragmentListWebHtmlViewModel::class.java
            )

    }

    override fun observable() {

    }
}