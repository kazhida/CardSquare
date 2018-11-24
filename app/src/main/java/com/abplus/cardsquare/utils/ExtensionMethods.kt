package com.abplus.cardsquare.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:imageUrl")
fun ImageView.imageUrl(url: String?) {
    GlideApp.with(context)
            .load(url)
            .centerCrop()
            .dontAnimate()
            .into(this)
}
