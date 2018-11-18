package com.abplus.cardsquare.domain.models

import android.content.Intent
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred

/**
 * アカウント
 * 各種プロバイダで認証されたアカウント
 * Firebaseの仕様にかなり依存している
 */
@Parcelize
data class Account(
        val uid: String,
        val provider: String,
        val name: String = ""
        // todo: リアクションのための情報が必要
) : Parcelable {

    interface Repository {
        fun find(uid: String): Deferred<Account?>
        fun find(uids: List<String>): Deferred<Map<String, Account>>
        fun saved(data: Intent?): Deferred<String>
    }
}
