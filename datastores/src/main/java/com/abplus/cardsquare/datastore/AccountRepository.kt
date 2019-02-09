package com.abplus.cardsquare.datastore

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.abplus.cardsquare.domain.entities.Account
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import timber.log.Timber

class AccountRepository : Account.Repository {

    companion object {
        const val ACCOUNTS = "accounts"
    }

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override val accountName: String get() = auth.currentUser?.displayName ?: "(no name)"

    override fun signIn(activity: FragmentActivity, idToken: String, requestCode: Int) {
        val client: GoogleApiClient by lazy {
            GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(idToken)
                    .requestEmail()
                    .build()
                    .let {
                        GoogleApiClient
                                .Builder(activity)
                                .enableAutoManage(activity, Listener(activity))
                                .addApi(Auth.GOOGLE_SIGN_IN_API, it)
                                .build()
                    }
        }
        val intent = Auth.GoogleSignInApi.getSignInIntent(client)
        activity.startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(data: Intent?) {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        if (result.isSuccess) {
            val account = result.signInAccount
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        throw Account.AccountException(task.exception?.message
                                ?: "")
                    }
                }
            }
        } else {
            throw Account.AccountException(CommonStatusCodes.getStatusCodeString(result.status.statusCode))
        }
    }

    private class Listener(private val context: Context) : GoogleApiClient.OnConnectionFailedListener {

        override fun onConnectionFailed(connectionResult: ConnectionResult) {
            Toast.makeText(context, connectionResult.errorMessage, Toast.LENGTH_SHORT).show()
            Timber.e("Google Sign in failed: %s", connectionResult.errorMessage)
        }
    }

    override suspend fun findByUserIdAsync(userRef: String): Deferred<List<Account>> = GlobalScope.async {
        store.collection(ACCOUNTS).whereEqualTo("refId", userRef).get().result?.mapNotNull {
            Account(
                    refId = it.reference.id,
                    userRef = it.getStringOrEmpty("refId"),
                    provider = Account.strToProvider(it.getStringOrEmpty("type")),
                    name = it.getStringOrEmpty("name"),
                    email = it.getStringOrEmpty("email"),
                    id = it.getLongOrZero("id"),
                    idAsString = it.getStringOrEmpty("idAsString")
            )
        } ?: ArrayList()
    }

    override suspend fun addAsGoogleAsync(
            userRef: String,
            name: String,
            email: String
    ): Deferred<Account?> = GlobalScope.async {
        val data = mapOf(
                "refId" to userRef,
                "name" to name,
                "email" to email
        )
        val task = store.collection(ACCOUNTS)
                .add(data)
                .queryAsync()
                .await()
        if (task.isSuccessful) {
            task.result?.let {
                Account(
                        refId = it.id,
                        userRef = userRef,
                        name = name,
                        email = email
                )
            }
        } else {
            null
        }
    }

    override suspend fun addAsTwitterAsync(userRef: String, name: String, id: Long, idAsString: String): Deferred<Account?> {
        return GlobalScope.async { null }
    }

    override suspend fun addAsFacebookAsync(userRef: String, name: String): Deferred<Account?> {
        return GlobalScope.async { null }
    }

    override suspend fun addAsGitHubAsync(userRef: String, name: String): Deferred<Account?> {
        return GlobalScope.async { null }
    }

    override suspend fun findSubCollectionByCardIdAsync(refId: String): Deferred<List<Account>> = GlobalScope.async {
        val s: CollectionReference = store.collection(ACCOUNTS).document(refId).collection(ACCOUNTS)
        s.get().result?.documents?.map {
            Account(
                    refId = it.reference.id,
                    userRef = it.getStringOrEmpty("refId"),
                    provider = Account.strToProvider(it.getStringOrEmpty("type")),
                    name = it.getStringOrEmpty("name"),
                    email = it.getStringOrEmpty("email"),
                    id = it.getLongOrZero("id"),
                    idAsString = it.getStringOrEmpty("idAsString")

            )
        } ?: ArrayList()
    }
}