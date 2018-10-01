package com.abplus.cardsquare.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.abplus.cardsquare.R
import com.abplus.cardsquare.entities.Card

class SquareCardView : CardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val inflater = LayoutInflater.from(context.applicationContext)
        inflater.inflate(R.layout.view_square_card, this)
    }

    val coverImage: ImageView by lazy { findViewById<ImageView>(R.id.card_cover_image) }
    val nameText: TextView by lazy { findViewById<TextView>(R.id.card_name_text) }
    val phoneticText: TextView by lazy { findViewById<TextView>(R.id.phonetic_text) }
    val introduceText: TextView by lazy { findViewById<TextView>(R.id.card_introduce_text) }
    val descriptionText: TextView by lazy { findViewById<TextView>(R.id.card_description_text) }
    val accountContainer: LinearLayout by lazy { findViewById<LinearLayout>(R.id.card_account_container) }

    fun update(card: Card) {

    }
}