package com.abplus.cardsquare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    companion object {
        fun start(activity: Activity, requestCode: Int) {
            Intent(activity, LoginActivity::class.java).let {
                activity.startActivityForResult(it, requestCode)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}