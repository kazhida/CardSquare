package com.abplus.cardsquare.app.domain.models

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
        fun findByUid(uid: String): Deferred<Account?>
        fun findByUids(uids: List<String>): Deferred<Map<String, Account>>
        fun find(refId: String): Deferred<Account?>
        fun saved(data: Intent?): Deferred<String>
    }

    companion object Providers {
        const val GOOGLE = "google"
        const val TWITTER = "twitter"
        const val FACEBOOK = "facebook"
        const val GITHUB = "github"

    }
}
