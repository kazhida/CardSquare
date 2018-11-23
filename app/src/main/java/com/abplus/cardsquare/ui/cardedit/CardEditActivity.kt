package com.abplus.cardsquare.ui.cardedit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.abplus.cardsquare.R
import com.abplus.cardsquare.databinding.ActivityCardEditBinding
import com.abplus.cardsquare.domain.models.Card
import com.abplus.cardsquare.utils.setupActionBar

class CardEditActivity : AppCompatActivity() {

    companion object {
        const val CARD = "CARD"

        fun start(activity: Activity, card: Card, requestCode: Int) {
            Intent(activity, CardEditActivity::class.java).let {
                it.putExtra(CARD, card)
                activity.startActivityForResult(it, requestCode)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCardEditBinding>(this, R.layout.activity_card_edit)
        binding.viewModel?.reset(intent.getParcelableExtra(CARD))

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
}
