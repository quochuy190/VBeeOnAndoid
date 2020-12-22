package com.vn.vbeeon.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeX(owner: LifecycleOwner, block: (T?) -> Unit) {
    this.observe(owner, Observer {
        block(it)
    })
}