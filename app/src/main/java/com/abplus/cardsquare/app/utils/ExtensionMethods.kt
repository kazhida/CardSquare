package com.abplus.cardsquare.app.utils

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
