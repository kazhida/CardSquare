package com.abplus.cardsquare.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred

/**
 * カード
 */
@Parcelize
data class Card(
        val refId: String = "",
        val userId: String = "",
        val title: String = "",
        val name: String = "",
        val firstName: String = "",
        val familyName: String = "",
        val coverImageUrl: String = "",
        val introduction: String = "",
        val description: String = "",
        val accounts: List<Account> = ArrayList(),
        val partners: List<Card> = ArrayList()
) : Parcelable {
    companion object {
        private const val CARDS = "CARDS"
    }

    interface Repository {

        suspend fun findCards(userId: String): Deferred<List<Card>>

        suspend fun add(
                userId: String,
                title: String,
                name: String,
                firstName: String,
                familyName: String,
                coverImageUrl: String,
                introduction: String,
                description: String,
                accounts: List<Account> = ArrayList(),
                partners: List<Card> = ArrayList()
        ): Deferred<Card?>
    }
}
