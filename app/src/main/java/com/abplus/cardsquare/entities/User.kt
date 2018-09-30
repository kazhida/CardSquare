package com.abplus.cardsquare.entities

/**
 * 現在サインインしているユーザ
 */
object User {
    @Suppress("unused")
    val userId: String get() = uid
    val cards: List<Card> = ArrayList()
    val accounts: List<Account> = ArrayList()

    private var uid: String = ""

    fun resetUserId(userId: String) {
        uid = userId
    }

    fun resetCards(cards: List<Card>) {
        (this.cards as? MutableList)?.let {
            it.clear()
            it.addAll(cards)
        }
    }

    fun resetAccounts(accounts: List<Account>) {
        (this.accounts as? MutableList)?.let {
            it.clear()
            it.addAll(accounts)
        }
    }
}
