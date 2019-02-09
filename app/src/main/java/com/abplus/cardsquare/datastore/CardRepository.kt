package com.abplus.cardsquare.datastore

import com.abplus.cardsquare.app.utils.RandomImages
import com.abplus.cardsquare.domain.entities.Account
import com.abplus.cardsquare.domain.entities.Card
import com.abplus.cardsquare.domain.entities.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CardRepository : Card.Repository {

    companion object {
        private const val CARDS = "CARDS"

        fun initialCard(user: User): Card = Card(
                refId = "",
                userId = user.refId,
                title = "1枚目のカード",
                name = user.defaultName,
                firstName = "John/Jane",
                familyName = "Doe",
                coverImageUrl = RandomImages.nextAssetImageUrl(),
                introduction = "ここでは、自己紹介文などを記入します。\nカードの右上に表示されます。",
                description = "ここには、なにを記入してもかまいません。\nカードの右下に表示されます。\nこのサービスでは住所は登録で来るようになっていないので、\n住所を明かす必要がある場合は、ここを使用してください。",
                accounts = user.accounts,
                partners = ArrayList()
        )
    }

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