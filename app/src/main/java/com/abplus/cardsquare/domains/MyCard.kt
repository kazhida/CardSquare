package com.abplus.cardsquare.domains

import com.abplus.cardsquare.entities.Account
import com.abplus.cardsquare.entities.Card

class MyCard(
        private var card: Card,
        private val myAccounts: MutableList<Account>,
        private val myPartners: MutableList<Card.WithAccounts>
) : Card.WithAccounts {

    override val id: Long get() = card.id
    override val name: String get() = card.name
    override val phonetic: String get() = card.phonetic
    override val coverImageUrl: String get() = card.coverImageUrl
    override val faceIconUrl: String get() = card.faceIconUrl
    override val introduction: String get() = card.introduction
    override val accounts: List<Account> get() = myAccounts
    val partners: List<Card.WithAccounts> get() = myPartners

    class Holder(userId: Long) {
        val cards: List<MyCard> get() = ArrayList<MyCard>().apply {

        }
    }
}
