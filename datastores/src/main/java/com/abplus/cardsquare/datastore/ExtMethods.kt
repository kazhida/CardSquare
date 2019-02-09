package com.abplus.cardsquare.datastore

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal fun <T> Task<T>.queryAsync(): Deferred<Task<T>> = GlobalScope.async {
    suspendCoroutine<Task<T>> { continuation ->
        this@queryAsync.addOnCompleteListener { task ->
            continuation.resume(task)
        }.addOnFailureListener { task ->
            continuation.resumeWithException(task)
        }
    }
}

internal fun Query.queryAsync(): Deferred<QuerySnapshot> = GlobalScope.async {
    suspendCoroutine<QuerySnapshot> { continuation ->
        this@queryAsync.addSnapshotListener { snapshot, exception ->
            when {
                exception != null -> continuation.resumeWithException(exception)
                snapshot != null -> continuation.resume(snapshot)
            }
        }
    }
}

internal fun DocumentSnapshot.getStringOrEmpty(field: String): String = getString(field) ?: ""
internal fun DocumentSnapshot.getLongOrZero(field: String): Long = getLong(field) ?: 0
internal val DocumentSnapshot.refId: String get() = reference.id
