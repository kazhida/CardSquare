package com.abplus.cardsquare.app.utils.views

import android.view.View
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class FadeoutOffsetChangeListener(private val header: View) : AppBarLayout.OnOffsetChangedListener {

    override fun onOffsetChanged(layout: AppBarLayout?, offset: Int) {
        if (layout != null) {
            // カードはだんだん消えていく
            val alpha = abs(offset.toFloat() / layout.totalScrollRange.toFloat())
            header.alpha = 1.0f - alpha
        }
    }
}
