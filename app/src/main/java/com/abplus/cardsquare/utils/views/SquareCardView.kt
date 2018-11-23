package com.abplus.cardsquare.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import com.abplus.cardsquare.R
import com.abplus.cardsquare.databinding.ViewSquareCardBinding


class SquareCardView : CardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val binding: ViewSquareCardBinding

    init {
        val inflater = LayoutInflater.from(context.applicationContext)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_square_card, this, true)
    }

    fun resetViewModel(viewModel: SquareCardViewModel) {
        binding.viewModel = viewModel
    }
}
