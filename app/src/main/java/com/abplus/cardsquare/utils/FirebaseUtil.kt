package com.abplus.cardsquare.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.suspendCoroutine

fun <T> Task<T>.promise(): Deferred<Task<T>> = GlobalScope.async {
    suspendCoroutine<Task<T>> { continuation ->
        this@promise.addOnCompleteListener {
            task -> continuation.resume(task)
        }.addOnFailureListener {
            task -> continuation.resumeWithException(task)
        }
    }
}

fun launchFB(proc: suspend ()->Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        proc()
    }
}
