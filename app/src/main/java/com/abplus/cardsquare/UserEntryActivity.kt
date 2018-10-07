package com.abplus.cardsquare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abplus.cardsquare.entities.Account
import com.abplus.cardsquare.entities.User
import com.abplus.cardsquare.utils.LogUtil
import com.abplus.cardsquare.utils.defer
import com.abplus.cardsquare.utils.launchFB
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class UserEntryActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private const val REQUEST_SIGN_IN = 4425

        fun start(activity: Activity, requestCode: Int) {
            Intent(activity, UserEntryActivity::class.java).let {
                activity.startActivityForResult(it, requestCode)
            }
        }
    }

    private val rootView: View  by lazy { findViewById<View>(R.id.root_view) }
    private val loginButton: View by lazy { findViewById<View>(R.id.google_login) }

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_entry)

        setResult(Activity.RESULT_CANCELED)

        loginButton.setOnClickListener {
            signIn()
        }
    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser
        if (user != null) {
            loggedIn(user)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                LogUtil.e(e.localizedMessage)
            }
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Toast.makeText(this, connectionResult.errorMessage, Toast.LENGTH_SHORT).show()
        LogUtil.e("Sign in failed: " + connectionResult.errorMessage)
    }

    private fun signIn() {
        val options = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        val client = GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build()
        val intent = Auth.GoogleSignInApi.getSignInIntent(client)
        startActivityForResult(intent, REQUEST_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            val user = if (task.isSuccessful) {
                auth.currentUser
            } else {
                Snackbar.make(rootView, R.string.err_cannot_login, Snackbar.LENGTH_SHORT).show()
                null
            }
            if (user != null) {
                loggedIn(user)
            }
        }
    }

    private fun loggedIn(user: FirebaseUser) {
        launchFB {
            // googleのaccountを登録
            val uid = user.uid
            val name = user.displayName ?: ""
            val email = user.email ?: ""
            val data = mapOf(
                    "uid" to uid,
                    "name" to name,
                    "email" to email
            )
            val task = store.collection("accounts")
                    .add(data)
                    .defer()
                    .await()
            val account = if (task.isSuccessful) {
                val ref = task.result
                Account.google(ref.id, uid, name, email)
            } else {
                null
            }
            if (account != null) {
                User.addAccount(account)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}
