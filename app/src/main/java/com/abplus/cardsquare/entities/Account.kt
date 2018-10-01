package com.abplus.cardsquare.entities

import com.abplus.cardsquare.utils.defer
import com.google.firebase.firestore.FirebaseFirestore
import org.parceler.Parcel

/**
 * カードに付加するSNSなどのアカウントのエンティティ
 */
@Parcel
class Account private constructor(
        val refId: String,
        val uid: String,
        val name: String,
        val email: String = "",
        val id: Long = 0,
        val idAsString: String = ""
) {
    // for parceler
    @Suppress("unused")
    private constructor() : this("", "", "")

    companion object {

        const val GOOGLE = "google"
        fun google(
                refId: String,
                uid: String,
                name: String,
                email: String
        ) = Account(
                refId = refId,
                uid = uid,
                name = name,
                email = email
        )

        const val TWITTER = "twitter"
        fun twitter(
                refId: String,
                uid: String,
                name: String,
                id: Long,
                idAsString: String
        ) = Account(
                refId = refId,
                uid = uid,
                name = name,
                id = id,
                idAsString = idAsString
        )

        const val FACEBOOK = "facebook"
        fun facebook(
                refId: String,
                uid: String,
                name: String
        ) = Account(refId, uid, name)

        const val GITHUB = "github"
        fun github(
                refId: String,
                uid: String,
                name: String
        ) = Account(refId, uid, name)
    }



    class Repository(private val store: FirebaseFirestore, private val uid: String) {

        suspend fun all(): List<Account> = ArrayList<Account>().apply {
            val accounts = store.collection("accounts")
                    .whereEqualTo("owner", uid)
                    .get()
                    .defer()
        }

        suspend fun addGoogle(name: String, email: String): Account? {
            val data = mapOf<String, Any>(
                    "name" to name,
                    "email" to email
            )
            val account = store.collection("accounts")
                    .add(data)
                    .defer()
            val task = account.await()
            return if (task.isSuccessful) {
                val ref = task.result
                ref.id
                google(ref.id, uid, name, email)
            } else {
                null
            }
        }

    }
}
