package com.abplus.cardsquare.entities

import android.os.Parcelable
import com.abplus.cardsquare.utils.asyncFB
import com.abplus.cardsquare.utils.defer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.experimental.Deferred

/**
 * カード
 */
@Parcelize
data class Card(
        val refId: String = "",
        val userId: String = "",
        val title: String = "",
        val name: String = "",
        val firstName: String = "",
        val familyName: String = "",
        val coverImageUrl: String = "",
        val introduction: String = "",
        val description: String = "",
        val accounts: List<Account> = ArrayList(),
        val partners: List<Card> = ArrayList()
) : Parcelable {
    companion object {
        private const val CARDS = "CARDS"
    }

    interface Repository {

        suspend fun findCards(userId: String): Deferred<List<Card>>

        suspend fun add(
                userId: String,
                title: String,
                name: String,
                firstName: String,
                familyName: String,
                coverImageUrl: String,
                introduction: String,
                description: String,
                accounts: List<Account> = ArrayList(),
                partners: List<Card> = ArrayList()
        ): Deferred<Card?>

        class Firebase : Repository {

            private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

            override suspend fun findCards(userId: String): Deferred<List<Card>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override suspend fun add(
                    userId: String,
                    title: String,
                    name: String,
                    firstName: String,
                    familyName: String,
                    coverImageUrl: String,
                    introduction: String,
                    description: String,
                    accounts: List<Account>,
                    partners: List<Card>
            ): Deferred<Card?> = asyncFB {
                val data = mapOf(
                        "userId" to userId,
                        "title" to title,
                        "name" to name,
                        "firstName" to firstName,
                        "familyName" to familyName,
                        "coverImageUrl" to coverImageUrl,
                        "introduction" to introduction,
                        "description" to description,
                        "accounts" to accounts,
                        "partners" to partners
                )
                val task = store.collection(CARDS)
                        .add(data)
                        .defer()
                        .await()
                if (task.isSuccessful) {
                    task.result?.let { ref ->
                        Card(
                                refId = ref.id,
                                userId = userId,
                                title = title,
                                name = name,
                                firstName = firstName,
                                familyName = familyName,
                                coverImageUrl = coverImageUrl,
                                introduction = introduction,
                                description = description
                        )
                    }
                } else {
                    null
                }

            }
        }
    }
}
