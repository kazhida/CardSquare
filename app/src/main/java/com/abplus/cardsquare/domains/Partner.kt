package com.abplus.cardsquare.domains

import com.abplus.cardsquare.entities.Account
import com.abplus.cardsquare.entities.Card

class Partner(private var card: Card, val accounts: List<Account>) : Card.WithAccounts {
    override val id: Long get() = card.id
    override val name: String get() = card.name
    override val phonetic: String get() = card.phonetic
    override val coverImageUrl: String get() = card.coverImageUrl
    override val faceIconUrl: String = card.faceIconUrl
    override val introduction: String = card.introduction
}