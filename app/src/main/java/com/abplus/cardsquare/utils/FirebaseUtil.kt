package com.abplus.cardsquare.utils

import android.app.Dialog
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
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

fun Query.defer(): Deferred<QuerySnapshot> = GlobalScope.async {
    suspendCoroutine<QuerySnapshot> { continuation ->
        this@defer.addSnapshotListener { snapshot, exception ->
            when {
                exception != null -> continuation.resumeWithException(exception)
                snapshot != null -> continuation.resume(snapshot)
            }
        }
    }
}

fun launchFB(proc: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        proc()
    }
}

fun launchFB(dialog: Dialog, proc: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        try {
            proc()
        } finally {
            dialog.dismiss()
        }
    }
}

fun <T, R> Task<T>.onSuccess(proc: (T) -> R): R? {
    return if (isSuccessful) {
        result?.let(proc)
    } else {
        null
    }
}

fun <T> asyncFB(block: suspend CoroutineScope.() -> T): Deferred<T> = GlobalScope.async(block = block)

fun DocumentSnapshot.getStringOrEmpty(field: String): String = getString(field) ?: ""
fun DocumentSnapshot.getLongOrZero(field: String): Long = getLong(field) ?: 0
val DocumentSnapshot.refId: String get() = reference.id
