package com.abplus.cardsquare.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.abplus.cardsquare.R
import com.abplus.cardsquare.entities.Account
import com.abplus.cardsquare.entities.Card
import com.abplus.cardsquare.utils.GlideApp

class SquareCardView : CardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        val inflater = LayoutInflater.from(context.applicationContext)
        inflater.inflate(R.layout.view_square_card, this)
    }

    val coverImage: ImageView           by lazy { findViewById<ImageView>(R.id.card_cover_image) }
    val nameText: TextView              by lazy { findViewById<TextView>(R.id.card_name_text) }
    val firstNameText: TextView         by lazy { findViewById<TextView>(R.id.card_first_name_text) }
    val familyNameText: TextView        by lazy { findViewById<TextView>(R.id.card_family_name_text) }
    val introductionText: TextView      by lazy { findViewById<TextView>(R.id.card_introduction_text) }
    val descriptionText: TextView       by lazy { findViewById<TextView>(R.id.card_description_text) }
    val accountContainer: LinearLayout  by lazy { findViewById<LinearLayout>(R.id.card_account_container) }

    private var latestImageUrl: String = ""

    fun update(card: Card) {
        if (card.coverImageUrl != latestImageUrl) {
            coverImage.loadUrl(card.coverImageUrl)
            latestImageUrl = card.coverImageUrl
        }

        nameText.text = card.name
        firstNameText.text = card.firstName
        familyNameText.text = card.familyName
        introductionText.text = card.introduction
        descriptionText.text = card.description

        if (card.accounts.isNotEmpty()) {
            accountContainer.removeAllViews()
            accountContainer.visibility = View.VISIBLE
            card.accounts.mapNotNull {
                accountView(accountContainer, it)
            }.forEach {
                accountContainer.addView(it)
            }
        } else {
            accountContainer.visibility = View.GONE
        }
    }

    private fun accountView(parent: ViewGroup, account: Account): ImageView? = when (account.type) {
        Account.Type.Google -> R.drawable.ic_sns_google_white
        Account.Type.Twitter -> R.drawable.ic_sns_twitter_white
        Account.Type.Facebook -> R.drawable.ic_sns_facebook_white
        Account.Type.GitHub -> R.drawable.ic_sns_github_white
        else -> null
    }?.let { iconId ->
        val icon = LayoutInflater.from(context).inflate(R.layout.icon_account, parent, false)
        (icon as? ImageView)?.apply {
            setImageResource(iconId)
            tag = account
        }
    }

    fun containsAccount(account: Account): Boolean {
        for (i in 0 until accountContainer.childCount) {
            val v = accountContainer.getChildAt(i)
            val a = v.tag
            if (a is Account) {
                if (a.refId == account.refId) {
                    return true
                }
            }
        }
        return false
    }

    fun addAccount(account: Account) {
        if (!containsAccount(account)) {
            accountContainer.addView(accountView(accountContainer, account))
        }
    }

    fun removeAccount(account: Account) {
        for (i in 0 until accountContainer.childCount) {
            val v = accountContainer.getChildAt(i)
            val a = v.tag
            if (a is Account) {
                if (a.refId == account.refId) {
                    accountContainer.removeView(v)
                }
            }
        }
    }

    private fun ImageView.loadUrl(url: String) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .dontAnimate()
                .into(this)
    }

    private fun accounts(): List<Account> {
        return ArrayList<Account>().apply {
            for (i in 0 until accountContainer.childCount) {
                val v = accountContainer.getChildAt(i)
                val a = v.tag
                if (a is Account) {
                    add(a)
                }
            }
        }
    }

    fun createCard(refId: String, partners: List<Card>): Card {
        return Card(
                refId = refId,
                uid = "",
                name = "",
                firstName = "",
                familyName = "",
                coverImageUrl = "",
                introduction = "",
                description = "",
                accounts = accounts(),
                partners = partners
        )
    }
}
