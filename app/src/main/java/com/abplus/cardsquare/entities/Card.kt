package com.abplus.cardsquare.entities

import org.parceler.Parcel
import java.io.FileDescriptor

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
        fun initial(): Card = Card(
                refId = "",
                uid = User.userId,
                name = "",
                firstName = "",
                familyName = "",
                coverImageUrl = "",
                introduction = "",
                description = "",
                accounts = User.accounts,
                partners = ArrayList()
        )
    }
}
