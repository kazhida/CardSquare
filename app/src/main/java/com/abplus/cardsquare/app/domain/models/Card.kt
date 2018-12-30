package com.abplus.cardsquare.app.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred

/**
 * カード
 * このアプリの主役
 * 他者に提供するユーザの情報をカード形式にまとめたもの
 */
@Parcelize
data class Card(
        val refId: String = "",
        val userId: String = "",
        val handleName: String = "",
        val firstName: String = "",
        val familyName: String = "",
        val coverImageUrl: String = "",
        val introduction: String = "",
        val description: String = "",
        val accounts: List<Account> = ArrayList(),
        val partners: List<Card> = ArrayList()
) : Parcelable {

    interface Repository {
        fun findByRefIds(refIds: List<String>): Deferred<List<Card>>
        fun findByUserId(userId: String): Deferred<List<Card>>
        fun add(
                userId: String,
                title: String,
                handleName: String,
                firstName: String,
                familyName: String,
                coverImageUrl: String,
                introduction: String,
                description: String,
                accounts: List<Account>
        ): Deferred<Card>
        fun Card.save(): Deferred<Card>
    }
}
