package com.abplus.cardsquare.datastore

import com.abplus.cardsquare.domain.entities.Account
import com.abplus.cardsquare.domain.entities.Card
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CardRepository : Card.Repository {

    companion object {
        private const val CARDS = "CARDS"
    }

    private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override suspend fun findCardsAsync(userId: String): Deferred<List<Card>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addAsync(
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
    ): Deferred<Card?> = GlobalScope.async {
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
                .queryAsync()
                .await()
        if (task.isSuccessful) {
            task.result?.let { ref ->
                Card(
                        refId = ref.id,
                        userId = userId,
                        title = title,
                        handleName = name,
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