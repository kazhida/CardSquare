package com.abplus.cardsquare.app.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule : AppGlideModule()

@BindingAdapter("app:imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    GlideApp.with(imageView.context)
            .load(url)
            .centerCrop()
            .dontAnimate()
            .into(imageView)

}
