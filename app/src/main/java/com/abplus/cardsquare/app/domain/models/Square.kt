package com.abplus.cardsquare.app.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred

/**
 * 広場
 * カードを公開する場所
 */
@Parcelize
data class Square(
        val refId: String,
        val name: String,
        val coverImageUrl: String,
        val cardIds: List<String>
) : Parcelable {

    interface Repository {
        fun all(): Deferred<List<Square>>
        fun add(name: String, coverImageUrl: String): Deferred<Square>
    }
}
