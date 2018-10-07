package com.abplus.cardsquare.entities

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * 現在サインインしているユーザ
 */
object User {
    @Suppress("unused")
    val userId: String get() = user?.uid ?: ""
    val defaultName: String get() = user?.displayName ?: ""
    val cards: List<Card> = ArrayList()
    val accounts: List<Account> = ArrayList()

    private val user: FirebaseUser? get() = FirebaseAuth.getInstance().currentUser

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

    fun addAccount(account: Account) {
        (this.accounts as? MutableList)?.add(account)
    }
}
