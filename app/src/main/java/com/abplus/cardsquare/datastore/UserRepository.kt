package com.abplus.cardsquare.datastore

import com.abplus.cardsquare.domain.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class UserRepository : User.Repository {

    companion object {
        private const val USERS = "users"
    }

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun find(uid: String): Deferred<User?> = GlobalScope.async {
        store.collection(USERS)
                .whereArrayContains("uids", uid)
                .queryAsync()
                .await()
                .documents
                .asSequence()
                .map {
                    User(it.refId, it.getStringOrEmpty("defaultName"))
                }
                .firstOrNull()
    }

    override fun current(): Deferred<User?> = GlobalScope.async {
        auth.currentUser?.uid?.let {
            find(it).await()
        }
    }

    override fun add(defaultName: String): Deferred<User?> = GlobalScope.async {
        val data = mapOf(
                "defaultName" to defaultName
        )
        val task = store.collection(USERS)
                .add(data)
                .queryAsync()
                .await()
        if (task.isSuccessful) {
            task.result?.let { ref ->
                User(ref.id, defaultName)
            }
        } else {
            null
        }
    }
}