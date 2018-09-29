package com.abplus.cardsquare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class LoginActivity : AppCompatActivity() {

    companion object {
        fun start(activity: Activity, requestCode: Int) {
            Intent(activity, LoginActivity::class.java).let {
                activity.startActivityForResult(it, requestCode)
            }
        }
    }

    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

    }
}