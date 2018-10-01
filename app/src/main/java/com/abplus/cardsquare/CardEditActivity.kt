package com.abplus.cardsquare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.abplus.cardsquare.entities.Card
import org.parceler.Parcels

class CardEditActivity : AppCompatActivity() {

    companion object {
        const val CARD = "CARD"

        fun start(activity: Activity, card: Card, requestCode: Int) {
            Intent(activity, CardEditActivity::class.java).let {
                it.putExtra(CARD, Parcels.wrap(card))
                activity.startActivityForResult(it, requestCode)
            }
        }
    }

    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    private val card: Card by lazy { Parcels.unwrap<Card>(intent.getParcelableExtra(CARD)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_edit)
        setSupportActionBar(toolbar)


    }
}
