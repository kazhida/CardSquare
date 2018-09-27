package com.abplus.cardsquare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

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