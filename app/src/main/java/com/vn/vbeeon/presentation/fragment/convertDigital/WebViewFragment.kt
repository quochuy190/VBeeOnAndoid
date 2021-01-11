package com.vn.vbeeon.presentation.fragment.convertDigital

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.FragmentActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.FragmentListWebHtmlViewModel
import kotlinx.android.synthetic.main.fragment_webv.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon

@Suppress("DEPRECATION")
class WebViewFragment : BaseFragment() {
    lateinit var mViewModel: FragmentListWebHtmlViewModel
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    companion object {
        fun newInstance(content: String, key: String): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString("content", content)
            args.putString("key", key)
            fragment.setArguments(args)
            return fragment
        }
    }

    var content: String = "";
    var key: String = "";
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("content")?.let {
            content = it
        }
        arguments?.getString("key")?.let {
            key = it
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
        tv_toolbar_title.text = "Chuyển đổi số"
        wvConvertDigital.getSettings().setJavaScriptEnabled(true);
        wvConvertDigital.setBackgroundColor(Color.TRANSPARENT);
        if (key.equals(ConstantCommon.KEY_WEBVIEW_URL)) {
            tv_toolbar_title.text = "vbeeon"
            tvSource.visibility = View.GONE
            wvConvertDigital.loadUrl(content)
        } else {
            content = "<font color='white'>" + content + "</font>";
            tvSource.visibility = View.VISIBLE
            wvConvertDigital.loadData(content, "text/html; charset=utf-8", "UTF-8");
        }
    }

    override fun initViewModel() {
        mViewModel =
            ViewModelProviders.of(activity as FragmentActivity, viewModelFactory).get(
                FragmentListWebHtmlViewModel::class.java
            )

    }

    override fun observable() {

    }
}