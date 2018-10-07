package com.abplus.cardsquare.entities

import com.abplus.cardsquare.utils.RandomImages
import org.parceler.Parcel

/**
 * カード
 */
@Parcel
data class Card(
        val refId: String,
        val uid: String,
        val name: String,
        val firstName: String,
        val familyName: String,
        val coverImageUrl: String,
        val introduction: String,
        val description: String,
        val accounts: List<Account> = ArrayList(),
        val partners: List<Card> = ArrayList()
) {
    // for parceler
    @Suppress("unused")
    private constructor() : this(
            refId = "",
            uid = "",
            name = "",
            firstName = "",
            familyName = "",
            coverImageUrl = "",
            introduction = "",
            description = "",
            accounts = ArrayList<Account>(),
            partners = ArrayList<Card>()
    )

    companion object {
        // 最初の一枚を作る時に使用するカード
        fun initial(): Card = Card(
                refId = "",
                uid = User.userId,
                name = User.defaultName,
                firstName = "John/Jane",
                familyName = "Doe",
                coverImageUrl = RandomImages.nextAssetImageUrl(),
                introduction = "ここでは、自己紹介文などを記入します。\nカードの右上に表示されます。",
                description = "ここには、なにを記入してもかまいません。\nカードの右下に表示されます。\nこのサービスでは住所は登録で来るようになっていないので、\n住所を明かす必要がある場合は、ここを使用してください。",
                accounts = User.accounts,
                partners = ArrayList()
        )
    }
}
