package com.abplus.cardsquare.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.suspendCoroutine

fun <T> Task<T>.defer(): Deferred<Task<T>> = GlobalScope.async {
    suspendCoroutine<Task<T>> { continuation ->
        this@defer.addOnCompleteListener {
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

fun <T, R> Task<T>.onSuccess(proc: (T)->R): R? {
    return if (isSuccessful) {
        proc(result)
    } else {
        null
    }
}

fun QueryDocumentSnapshot.getStringOrEmpty(field: String): String = getString(field) ?: ""
fun QueryDocumentSnapshot.getLongOrZero(field: String): Long = getLong(field) ?: 0
val QueryDocumentSnapshot.refId: String get() = reference.id