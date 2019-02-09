package com.abplus.cardsquare.app.ui.userentry

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.abplus.cardsquare.app.R
import com.abplus.cardsquare.di.UseCaseFactory
import com.abplus.cardsquare.domain.usecases.UserUseCase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

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

    private val useCase: UserUseCase by lazy { UseCaseFactory.createUserUseCase() }

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

        GlobalScope.launch {
            val holder = useCase.currentUser()
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
                useCase.onActivityResult(data)
                finish()
            } catch (e: Exception) {
                val message = e.message
                Timber.e("Sign-in error: $message")
                Snackbar.make(rootView, R.string.err_cannot_login, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn() {
        useCase.signIn(this,getString(R.string.default_web_client_id),  REQUEST_SIGN_IN)
    }
}
