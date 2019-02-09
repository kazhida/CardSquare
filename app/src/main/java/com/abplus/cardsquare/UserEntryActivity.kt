package com.abplus.cardsquare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abplus.cardsquare.datastore.AccountRepository
import com.abplus.cardsquare.datastore.CardRepository
import com.abplus.cardsquare.datastore.UserRepository
import com.abplus.cardsquare.domain.UserDomain
import com.abplus.cardsquare.domain.entities.Account
import com.abplus.cardsquare.utils.LogUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserEntryActivity : AppCompatActivity() {

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

    private val userDomain: UserDomain by lazy {
        UserDomain(
                UserRepository(),
                AccountRepository(),
                CardRepository()
        )
    }

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

        GlobalScope.launch {
            val user = userDomain.currentUser().await()
            if (user != null) {
                // 登録ができたので終わる
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SIGN_IN) {
            try {
                userDomain.onActivityResult(data)
            } catch (e: Account.AccountException) {
                val message = e.message
                LogUtil.e("Sign-in error: $message")
                Snackbar.make(rootView, R.string.err_cannot_login, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn() {
        userDomain.signIn(this, REQUEST_SIGN_IN)
    }
}
