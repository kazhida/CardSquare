package com.abplus.cardsquare.entities

/**
 * カードのエンティティに必要な物とその実装
 *
 * カードにはアカウントがつくので、
 * それを加えたインターフェースもここに用意した
 */
interface Card {
    val id: Long
    val name: String
    val phonetic: String
    val coverImageUrl: String
    val faceIconUrl: String
    val introduction: String

    data class Implement(
            override val id: Long,
            override val name: String,
            override val phonetic: String,
            override val coverImageUrl: String,
            override val faceIconUrl: String,
            override val introduction: String
    ) : Card

    interface WithAccounts : Card {
        val accounts: List<Account>
    }
}
