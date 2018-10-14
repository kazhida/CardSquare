package com.abplus.cardsquare.entities

import com.abplus.cardsquare.utils.asyncFB
import com.abplus.cardsquare.utils.defer
import com.abplus.cardsquare.utils.getStringOrEmpty
import com.abplus.cardsquare.utils.refId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.experimental.Deferred

data class User(
        val refId: String = "",
        val defaultName: String = "",
        val accounts: List<Account> = ArrayList(),
        val cards: List<Card> = ArrayList()
) {
    companion object {
        private const val USERS = "users"
    }

    interface Repository {
        fun find(uid: String): Deferred<User?>
        fun current(): Deferred<User?>
        fun add(defaultName: String ): Deferred<User?>

        class Firebase : Repository {

            private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
            private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

            override fun find(uid: String): Deferred<User?> = asyncFB {
                store.collection(USERS)
                        .whereArrayContains("uids", uid)
                        .defer()
                        .await()
                        .documents
                        .asSequence()
                        .map {
                            User(it.refId, it.getStringOrEmpty("defaultName"))
                        }.firstOrNull()
            }

            override fun current(): Deferred<User?> = asyncFB  {
                auth.currentUser?.uid?.let {
                    find(it).await()
                }
            }

            override fun add(defaultName: String): Deferred<User?> = asyncFB {
                val data = mapOf(
                        "defaultName" to defaultName
                )
                val task = store.collection(USERS)
                        .add(data)
                        .defer()
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
    }
}

