package com.abplus.cardsquare.app.utils

import java.util.*

object RandomImages {
    private val random = Random(System.currentTimeMillis())

    fun nextAssetImageUrl(): String {
        val i = random.nextInt(10)
        return "file:///android_asset/images/card_$i.png"
    }
}
