package com.abplus.cardsquare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.abplus.cardsquare.utils.LogUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class UserEntryActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_SIGN_IN = 4425
        const val USER = "USER"

        fun start(activity: Activity, requestCode: Int) {
            Intent(activity, UserEntryActivity::class.java).let {
                activity.startActivityForResult(it, requestCode)
            }
        }
    }

    private val rootView: View  by lazy { findViewById<View>(R.id.root_view) }
    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val loginButton: View by lazy { findViewById<View>(R.id.google_login) }

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_entry)
        setSupportActionBar(toolbar)

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

        if (requestCode == REQUEST_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                LogUtil.e(e.localizedMessage)
            }
        }
    }

    private fun signIn() {
        val options = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .requestProfile()
                .build()
        val client = GoogleSignIn.getClient(this, options)
        val intent = client.signInIntent
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



        Intent().apply {
            putExtra(USER, user)
        }.let {
            setResult(Activity.RESULT_OK, it)
            finish()
        }
    }
}