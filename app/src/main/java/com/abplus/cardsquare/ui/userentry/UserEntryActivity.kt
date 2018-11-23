package com.abplus.cardsquare.ui.userentry

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abplus.cardsquare.R
import com.abplus.cardsquare.domain.usecases.HolderUseCase
import com.abplus.cardsquare.utils.LogUtil
import com.abplus.cardsquare.utils.launchUI
import com.google.android.material.snackbar.Snackbar

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

    private val holderUseCase: HolderUseCase by lazy { HolderUseCase.firebaseInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_entry)

        setResult(Activity.RESULT_CANCELED)

        loginButton.setOnClickListener {
            signIn()
        }
    }

    override fun onResume() {
        super.onResume()

        launchUI {
            val holder = holderUseCase.currentHolder.await()
            if (holder != null) {
                // 登録ができたので終わる
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SIGN_IN) {
            try {
                val task = holderUseCase.onActivityResult(data)
                launchUI {
                    if (task.await() != null) {
                        finish()
                    }
                }
            } catch (e: Exception) {
                val message = e.message
                LogUtil.e("Sign-in error: $message")
                Snackbar.make(rootView, R.string.err_cannot_login, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn() {
        holderUseCase.signIn(this, REQUEST_SIGN_IN) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            LogUtil.e("Google Sign in failed: $errorMessage")
        }
    }
}
