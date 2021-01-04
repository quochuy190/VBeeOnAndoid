package com.vn.vbeeon.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.view.RxView
import com.vn.vbeeon.R
import com.vn.vbeeon.utils.SafeClickListener
import java.util.concurrent.TimeUnit

fun Activity.openFragment(
    fragment: Fragment,
    addToBackStack: Boolean
) {
    this as AppCompatActivity
    supportFragmentManager.beginTransaction().apply {
        add(R.id.fragmentConvertDigital, fragment)
        if (addToBackStack) addToBackStack(fragment::class.java.simpleName)
        commit()
    }
}

fun View.setOnClickAction(listener: View.OnClickListener) {
    RxView.clicks(this)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe { listener.onClick(this) }
}

fun View.setOnClickAction(action: (view: View) -> Unit) {
    RxView.clicks(this)
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .subscribe { action(this) }
}

inline fun View.handleFocusChange(context: Context, noinline onViewFocused:() -> Unit){
    this.setOnFocusChangeListener { v, gainFocus ->
        if (gainFocus) {
            val anim = AnimationUtils.loadAnimation(context, R.anim.scale_in_tv)
            anim.fillAfter = true
            this.startAnimation(anim)

            ViewCompat.setElevation(this, 1F)
            onViewFocused.invoke()
        } else {
            val anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_tv)
            anim.fillAfter = true
            this.startAnimation(anim)

            ViewCompat.setElevation(this, 0F)
        }
    }
}

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivity(intent, options)
    } else {
        startActivity(intent)
    }
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)

fun View.setOnSafeClickListener(
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(SafeClickListener { v ->
        onSafeClick(v)
    })
}

fun View.setOnSafeClickListener(
    interval: Int,
    onSafeClick: (View) -> Unit
) {
    setOnClickListener(SafeClickListener(interval, {v->
        onSafeClick(v)
    }))
}
