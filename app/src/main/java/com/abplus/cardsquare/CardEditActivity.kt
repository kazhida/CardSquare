package com.abplus.cardsquare

import android.app.Activity
import android.content.Intent

class CardEditActivity  {

    companion object {
        fun start(activity: Activity, requestCode: Int) {
            Intent(activity, CardEditActivity::class.java).let {
                activity.startActivityForResult(it, requestCode)
            }
        }
    }


}