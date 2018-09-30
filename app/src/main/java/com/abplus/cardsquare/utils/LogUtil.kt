package com.abplus.cardsquare.utils

import android.util.Log
import com.abplus.cardsquare.BuildConfig

object LogUtil {
    private val tag: String
        get() {
            val stackTraceElement = Thread.currentThread().stackTrace[4]

            var className = stackTraceElement.className
            val lineNumber = stackTraceElement.lineNumber

            className = className.substring(className.lastIndexOf("") + 1)

            return "$className:$lineNumber"
        }

    @Suppress("unused")
    fun e(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg ?: "(null)")
        }
    }

    @Suppress("unused")
    fun w(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg ?: "(null)")
        }
    }

    @Suppress("unused")
    fun i(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg ?: "(null)")
        }
    }

    @Suppress("unused")
    fun d(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg ?: "(null)")
        }
    }

    @Suppress("unused")
    fun v(msg: String?) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, msg ?: "(null)")
        }
    }

}