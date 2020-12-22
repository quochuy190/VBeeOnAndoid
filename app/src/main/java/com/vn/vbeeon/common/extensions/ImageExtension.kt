package com.vn.vbeeon.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vn.vbeeon.R


fun ImageView.loadImage(url: String?){
    val option = RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
    Glide.with(this)
        .load(url)
        .apply(option)
        .into(this)
}