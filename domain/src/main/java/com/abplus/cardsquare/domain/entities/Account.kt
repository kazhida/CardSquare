package com.abplus.cardsquare.domain.entities

import android.content.Intent
import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred
import java.lang.Exception

/**
 * カードに付加するSNSなどのアカウントのエンティティ
 */
@Parcelize
data class Account(
        val refId: String = "",
        val userRef: String = "",
        val provider: AuthProvider = AuthProvider.Unknown,
        val name: String = "",
        val email: String = "",
        val id: Long = 0,
        val idAsString: String = ""
) : Parcelable {
    enum class AuthProvider {
        Unknown,
        Google,
        Twitter,
        Facebook,
        GitHub
    }

    companion object {
        const val GOOGLE = "google"
        const val TWITTER = "twitter"
        const val FACEBOOK = "facebook"
        const val GITHUB = "github"

        fun strToProvider(provider: String): AuthProvider = when (provider) {
            GOOGLE -> AuthProvider.Google
            TWITTER -> AuthProvider.Twitter
            FACEBOOK -> AuthProvider.Facebook
            GITHUB -> AuthProvider.GitHub
            else -> AuthProvider.Unknown
        }
    }

    class AccountException(message: String) : Exception(message)

    interface Repository {
        fun signIn(activity: FragmentActivity, requestCode: Int)
        fun onActivityResult(data: Intent?)
        val accountName: String

        suspend fun findByUserId(userRef: String): Deferred<List<Account>>

        suspend fun addAsGoogle(
                userRef: String,
                name: String,
                email: String
        ): Deferred<Account?>
        suspend fun addAsTwitter(
                userRef: String,
                name: String,
                id: Long,
                idAsString: String
        ): Deferred<Account?>
        suspend fun addAsFacebook(
                userRef: String,
                name: String
        ): Deferred<Account?>
        suspend fun addAsGitHub(
                userRef: String,
                name: String
        ): Deferred<Account?>

        suspend fun findSubCollectionByCardId(refId: String): Deferred<List<Account>>
    }
}
