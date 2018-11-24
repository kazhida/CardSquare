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
import com.abplus.cardsquare.ui.common.SquareCardFragment
import com.abplus.cardsquare.utils.views.FadeoutOffsetChangeListener

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

    private val squareCardFragment: SquareCardFragment? by lazy {
        supportFragmentManager.findFragmentById(R.id.square_card) as? SquareCardFragment
    }

    private val binding: ActivityCardEditBinding by lazy {
        DataBindingUtil.setContentView<ActivityCardEditBinding>(this, R.layout.activity_card_edit)
    }

    private val card: Card by lazy {
        intent.getParcelableExtra<Card>(CARD)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.let {
            it.setLifecycleOwner(this)
            it.viewModel = ViewModelProviders.of(this).get(CardEditViewModel::class.java)
        }

        binding.appBar.addOnOffsetChangedListener(FadeoutOffsetChangeListener(binding.squareCardFrame))

        setSupportActionBar(binding.toolbar)

        if (card.refId.isNotEmpty()) {
            supportActionBar?.run {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val editViewModel = binding.viewModel
        val cardViewModel = squareCardFragment?.viewModel

        if (editViewModel != null && cardViewModel != null) {
            observeTransfer(editViewModel.handleName, cardViewModel.handleName)
            observeTransfer(editViewModel.firstName, cardViewModel.firstName)
            observeTransfer(editViewModel.familyName, cardViewModel.familyName)
            observeTransfer(editViewModel.coverImageUrl, cardViewModel.coverImageUrl)
            observeTransfer(editViewModel.introduction, cardViewModel.introduction)
            observeTransfer(editViewModel.description, cardViewModel.description)
            editViewModel.reset(card)
            cardViewModel.initAccountIcons(card.accounts)
        }
    }

    private fun observeTransfer(src: LiveData<String>, dst: MutableLiveData<String>?) {
        Observer<String> {
            dst?.value = it
        }.let {
            val owner = this
            src.observe(owner, it)
        }
    }
}
