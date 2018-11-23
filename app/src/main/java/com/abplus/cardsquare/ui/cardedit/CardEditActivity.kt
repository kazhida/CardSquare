package com.abplus.cardsquare.ui.cardedit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abplus.cardsquare.R
import com.abplus.cardsquare.databinding.ActivityCardEditBinding
import com.abplus.cardsquare.domain.models.Card
import com.abplus.cardsquare.utils.setupActionBar
import com.abplus.cardsquare.utils.views.SquareCardView

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

    private val squareCardView: SquareCardView by lazy { findViewById<SquareCardView>(R.id.square_card) }
    private lateinit var binding: ActivityCardEditBinding
    private val card: Card by lazy { intent.getParcelableExtra<Card>(CARD) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_edit)
        binding.viewModel = ViewModelProviders.of(this).get(CardEditViewModel::class.java)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.viewModel?.run {
            squareCardView.resetViewModel(cardViewModel)
            observeTransfer(handleName, cardViewModel.handleName)
            observeTransfer(firstName, cardViewModel.firstName)
            observeTransfer(familyName, cardViewModel.familyName)
            observeTransfer(coverImageUrl, cardViewModel.coverImageUrl)
            observeTransfer(introduction, cardViewModel.introduction)
            observeTransfer(description, cardViewModel.description)
            reset(card)
        }
    }

    private fun observeTransfer(src: LiveData<String>, dst: MutableLiveData<String>) {
        Observer<String> {
            dst.value = it ?: ""
        }.let {
            val owner = this
            src.observe(owner, it)
        }
    }
}
