package com.abplus.cardsquare.entities

import org.parceler.Parcel
import java.io.FileDescriptor

/**
 * カード
 */
@Parcel
data class Card(
        val id: String,
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
            id = "",
            name = "",
            firstName = "",
            familyName = "",
            coverImageUrl = "",
            introduction = "",
            description = "",
            accounts = ArrayList<Account>(),
            partners = ArrayList<Card>()
    )
}
