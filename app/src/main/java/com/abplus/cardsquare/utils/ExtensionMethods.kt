package com.abplus.cardsquare.utils

import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter

fun Boolean.ifTrue(proc: ()->Unit): Boolean = also {
    if (it) {
        proc()
    }
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}


@BindingAdapter("app:imageUrl")
fun ImageView.imageUrl(url: String?) {
    GlideApp.with(context)
            .load(url)
            .centerCrop()
            .dontAnimate()
            .into(this)
}
