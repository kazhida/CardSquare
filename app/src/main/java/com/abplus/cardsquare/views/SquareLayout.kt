package com.abplus.cardsquare.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

class SquareLayout : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 無理矢理正方形にする
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}