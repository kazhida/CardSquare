package com.abplus.cardsquare.entities

import android.provider.ContactsContract
import com.google.firebase.firestore.FirebaseFirestore
import org.parceler.Parcel

/**
 * カードに付加するSNSなどのアカウントのエンティティ
 */
@Parcel
class Account private constructor(
        @Suppress("unused")
        val uid: String,
        @Suppress("unused")
        val name: String = "",
        @Suppress("unused")
        val email: String = "",
        @Suppress("unused")
        val id: Long = 0,
        @Suppress("unused")
        val idAsString: String = ""
) {
    // for parceler
    @Suppress("unused")
    private constructor() : this("")

    companion object {

        const val GOOGLE = "google"
        fun google(
                uid: String,
                name: String,
                email: String
        ) = Account(
                uid = uid,
                name = name,
                email = email
        )

        const val TWITTER = "twitter"
        fun twitter(
                uid: String,
                name: String,
                id: Long,
                idAsString: String
        ) = Account(
                uid = uid,
                name = name,
                id = id,
                idAsString = idAsString
        )

        const val FACEBOOK = "facebook"
        fun facebook(
            uid: String,
            name: String
        ) = Account(uid, name)

        const val GITHUB = "github"
        fun github(
                uid: String,
                name: String
        ) = Account(uid, name)
    }

    class Repository(private val store: FirebaseFirestore, private val uid: String) {

        suspend fun addGoogle(name: String, email: String) {
            val data = mapOf<String, Any>(
                    "name" to name,
                    "email" to email
            )
            store.collection("accounts")
                    .add(data)
                    .addOnSuccessListener {

                    }


        }

    }
}
