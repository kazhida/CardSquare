package com.abplus.cardsquare.data.firebase.repositories

import android.content.Intent
import com.abplus.cardsquare.domain.models.Account
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.lang.Exception


class AccountRepository : FirebaseRepository(), Account.Repository {

    companion object {
        private const val ACCOUNTS = "ACCOUNTS"
    }

    override fun find(refId: String): Deferred<Account?> = GlobalScope.async {
        store.collection(ACCOUNTS)
                .document(refId)
                .get()
                .defer()
                .await()
                .result?.let {
                    Account(
                            uid = it.getStringOrEmpty("uid"),
                            provider = it.getStringOrEmpty("provider"),
                            name = it.getStringOrEmpty("handleName")
                    )
                }
    }

    override fun findByUid(uid: String): Deferred<Account?> = GlobalScope.async {
        store.collection(ACCOUNTS)
                .whereEqualTo("uid", uid)
                .defer()
                .await()
                .documents
                .firstOrNull()?.let {
                    Account(
                            uid = it.getStringOrEmpty("uid"),
                            provider = it.getStringOrEmpty("provider"),
                            name = it.getStringOrEmpty("handleName")
                    )
                }
    }

    override fun findByUids(uids: List<String>): Deferred<Map<String, Account>> = GlobalScope.async {
        HashMap<String, Account>().apply {
            uids.forEach {
                uid -> find(uid).await()?.let {
                    put(it.uid, it)
                }
            }
        }
    }

    override fun saved(data: Intent?): Deferred<String> = GlobalScope.async {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        if (result.isSuccess) {
            val account = result.signInAccount
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                val task = auth.signInWithCredential(credential).defer().await()
                task.result?.user?.let {
                    it.getAccount() ?: it.addAccount()
                } ?: throw Exception("account cannot add")
            } else {
                throw Exception("account not found")
            }
        } else {
            throw Exception(CommonStatusCodes.getStatusCodeString(result.status.statusCode))
        }
    }

    private suspend fun FirebaseUser.getAccount(): String? = store.collection(ACCOUNTS)
            .whereEqualTo("uid", uid)
            .defer()
            .await()
            .documents
            .firstOrNull()?.id

    private suspend fun FirebaseUser.addAccount(): String? {
        val data = mapOf(
                "uid" to uid,
                "provider" to providerId,
                "handleName" to displayName
        )
        return store.collection(ACCOUNTS).add(data).defer().await().result?.id
    }
}
