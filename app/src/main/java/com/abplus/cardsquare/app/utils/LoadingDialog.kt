package com.abplus.cardsquare.app.utils

import android.app.Dialog
import android.content.Context
import com.abplus.cardsquare.app.R

object LoadingDialog {

    fun show(context: Context): Dialog = Dialog(context, R.style.ProgressDialog).apply {
        setContentView(R.layout.dailog_progress)
        setCancelable(false)
        show()
    }
}