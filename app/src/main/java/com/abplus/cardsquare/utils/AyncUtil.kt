package com.abplus.cardsquare.utils

import android.app.Dialog
import kotlinx.coroutines.*

fun launchUI(proc: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        proc()
    }
}

fun launchUI(dialog: Dialog, proc: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        try {
            proc()
        } finally {
            dialog.dismiss()
        }
    }
}

fun <T> asyncBlock(block: suspend CoroutineScope.() -> T): Deferred<T> = GlobalScope.async(block = block)

