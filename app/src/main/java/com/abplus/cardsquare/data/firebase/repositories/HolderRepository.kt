package com.abplus.cardsquare.data.firebase.repositories

import androidx.fragment.app.FragmentActivity
import com.abplus.cardsquare.R
import com.abplus.cardsquare.data.firebase.HolderData
import com.abplus.cardsquare.domain.models.Account
import com.abplus.cardsquare.domain.models.Holder
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class HolderRepository : FirebaseRepository(), Holder.Repository {

    companion object {
        private const val HOLDERS = "HOLDERS"
    }

    private val accountRepository = AccountRepository()
    //private val cardRepository = CardRepository()

    override val current: Deferred<Holder?> get() {
        val uid = auth.currentUser?.uid
        return if (uid != null) {
            findByAccountId(uid)
        } else {
            GlobalScope.async { null }
        }
    }

    override fun findByAccountId(accountId: String): Deferred<Holder?> = GlobalScope.async {
        store.collection(HOLDERS)
                .whereArrayContains("accounts", accountId)
                .defer()
                .await()
                .documents
                .asSequence()
                .firstOrNull()?.let {
                    val data = it.toObject(HolderData::class.java)
                    if (data is HolderData) {
                        val accounts = accountRepository.findByUids(data.accounts).await()
                        Holder(
                                refId = it.refId,
                                defaultName = data.defaultName,
                                accounts = accounts
                        )
                    } else {
                        null
                    }
                }
    }

    override fun create(account: Account): Deferred<Holder?> = GlobalScope.async {
        val holder = HolderData(
                defaultName = account.name,
                accounts = listOf(account.uid)
        )
        store.collection(HOLDERS).add(holder).defer().await()
        findByAccountId(account.uid).await()
    }

    override fun signIn(activity: FragmentActivity, requestCode: Int, onFailed: (errorMessage: String)->Unit) {
        val client: GoogleApiClient by lazy {
            GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(activity.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                    .let {
                        GoogleApiClient
                                .Builder(activity)
                                .enableAutoManage(activity, Listener(onFailed))
                                .addApi(Auth.GOOGLE_SIGN_IN_API, it)
                                .build()
                    }
        }
        val intent = Auth.GoogleSignInApi.getSignInIntent(client)
        activity.startActivityForResult(intent, requestCode)
    }

    private class Listener(private val onFailed: (errorMessage: String) -> Unit) : GoogleApiClient.OnConnectionFailedListener {

        override fun onConnectionFailed(connectionResult: ConnectionResult) {
            onFailed(connectionResult.errorMessage ?: "")
        }
    }


    /*


            override fun onActivityResult(data: Intent?) {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if (result.isSuccess) {
                    val account = result.signInAccount
                    if (account != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        auth.signInWithCredential(credential).addOnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                throw AccountException(task.exception?.message ?: "")
                            }
                        }
                    }
                } else {
                    throw AccountException(CommonStatusCodes.getStatusCodeString(result.status.statusCode))
                }
            }

    fun onActivityResult(data: Intent?) {
        accountRepository.onActivityResult(data)
        userRepository.add(accountRepository.accountName)
    }

            override fun onActivityResult(data: Intent?) {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if (result.isSuccess) {
                    val account = result.signInAccount
                    if (account != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        auth.signInWithCredential(credential).addOnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                throw AccountException(task.exception?.message ?: "")
                            }
                        }
                    }
                } else {
                    throw AccountException(CommonStatusCodes.getStatusCodeString(result.status.statusCode))
                }
            }



     */
}