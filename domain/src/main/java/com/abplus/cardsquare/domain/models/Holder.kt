package com.abplus.cardsquare.domain.models

import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred

/**
 * ユーザ
 */
@Parcelize
data class Holder(
        val refId: String = "",
        val defaultName: String = "",
        val accounts: Map<String, Account> = HashMap(),
        val cards: List<Card> = ArrayList()
) : Parcelable {

    private fun Map<String, Account>.append(accounts: Map<String, Account>) = HashMap<String, Account>().apply {
        putAll(this@append)
        putAll(accounts)
    }

    @Suppress("unused")
    private fun List<Card>.append(cards: List<Card>) = ArrayList<Card>().apply {
        addAll(this@append)
        addAll(cards)
    }

    fun add(accounts: Map<String, Account>): Holder = copy(accounts = accounts.append(accounts))

    interface Repository {
        fun signIn(activity: FragmentActivity, requestCode: Int, onFailed: (errorMessage: String)->Unit)
        val current: Deferred<Holder?>
        fun findByAccountId(accountId: String): Deferred<Holder?>
        fun create(account: Account): Deferred<Holder?>
    }
}
