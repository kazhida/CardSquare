package com.abplus.cardsquare.utils

import android.app.Dialog
import android.content.Context
import com.abplus.cardsquare.R

object LoadingDialog {

    fun show(context: Context): Dialog = Dialog(context, R.style.ProgressDialog).apply {
        setContentView(R.layout.dailog_progress)
        setCancelable(false)
        show()
    }
}