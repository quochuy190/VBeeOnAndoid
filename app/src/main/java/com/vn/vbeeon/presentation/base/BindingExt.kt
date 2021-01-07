package com.vn.vbeeon.presentation.base

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.vn.vbeeon.R
import com.vn.vbeeon.domain.model.Device

@BindingAdapter("enable")
fun setEnable(view: View, enable: Boolean) {
    view.isEnabled = enable
}



@BindingAdapter("setDeviceSource")
fun setDeviceSource(view: ImageView, device: Device) {
    if(device.intSource > 0) {
        Glide.with(view)
            .load(device.intSource)
            .into(view)
    } else {
        Glide.with(view)
            .load(R.drawable.img_device_sphyg)
            .into(view)
    }
}
