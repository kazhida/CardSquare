package com.abplus.cardsquare.ui.cardedit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.abplus.cardsquare.R
import com.abplus.cardsquare.domain.models.Account
import com.abplus.cardsquare.domain.models.Card
import com.abplus.cardsquare.utils.views.SquareCardView
import com.google.android.material.textfield.TextInputEditText

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

    private val toolbar: Toolbar                    by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val tutorialText0: View                 by lazy { findViewById<View>(R.id.tutorial_text_0) }
    private val tutorialText1: View                 by lazy { findViewById<View>(R.id.tutorial_text_1) }
    private val squareCard: SquareCardView          by lazy { findViewById<SquareCardView>(R.id.square_card) }
    private val nameEdit: TextInputEditText         by lazy { findViewById<TextInputEditText>(R.id.name_edit) }
    private val firstNameEdit: TextInputEditText    by lazy { findViewById<TextInputEditText>(R.id.first_name_edit) }
    private val familyNameEdit: TextInputEditText   by lazy { findViewById<TextInputEditText>(R.id.family_name_edit) }
    private val introductionEdit: TextInputEditText by lazy { findViewById<TextInputEditText>(R.id.introduction_edit) }
    private val descriptionEdit: TextInputEditText  by lazy { findViewById<TextInputEditText>(R.id.description_edit) }
    private val accountContainer: LinearLayout      by lazy { findViewById<LinearLayout>(R.id.account_container) }

    private val card: Card by lazy { intent.getParcelableExtra<Card>(CARD) }
    private val isInitial: Boolean get() = card.refId.isEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_edit)
        setSupportActionBar(toolbar)

        if (isInitial) {
            tutorialText0.visibility = View.VISIBLE
            tutorialText1.visibility = View.VISIBLE
        } else {
            tutorialText0.visibility = View.GONE
            tutorialText1.visibility = View.GONE
            squareCard.setOnClickListener {
                //todo: 画像選択
            }
        }

        nameEdit.apply {
            setText(card.name)
            addTextChangedListener(Binder(this, squareCard.nameText))
        }
        firstNameEdit.apply {
            setText(card.firstName)
            addTextChangedListener(Binder(this, squareCard.firstNameText))
        }
        familyNameEdit.apply {
            setText(card.familyName)
            addTextChangedListener(Binder(this, squareCard.familyNameText))
        }
        introductionEdit.apply {
            setText(card.introduction)
            addTextChangedListener(Binder(this, squareCard.introductionText))
        }
        descriptionEdit.apply {
            setText(card.description)
            addTextChangedListener(Binder(this, squareCard.descriptionText))
        }

        squareCard.update(card)

        card.accounts.mapNotNull {
            accountView(it)
        }.forEach {
            accountContainer.addView(it)
        }
    }

    override fun onStop() {
        super.onStop()
        val intent = Intent().apply {
            putExtra(CARD, squareCard.createCard(card.refId, card.partners))
        }
        setResult(Activity.RESULT_OK, intent)
    }

    private fun accountView(account: Account): View? {
        return layoutInflater.inflate(R.layout.view_account_item, accountContainer, false).apply {
            findViewById<TextView>(R.id.account_caption).apply {
                text = account.name
                isSelected = squareCard.containsAccount(account)
            }
            tag = account
            setOnClickListener {
                isSelected = !isSelected
                if (isSelected) {
                    squareCard.addAccount(account)
                } else {
                    squareCard.removeAccount(account)
                }
            }
        }
    }

    private class Binder(private val src: TextInputEditText, private val dst: TextView) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            dst.text = src.text?.toString()?.trim() ?: ""
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}
