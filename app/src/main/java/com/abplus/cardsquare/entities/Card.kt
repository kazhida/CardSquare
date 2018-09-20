package com.abplus.cardsquare.entities

/**
 * カードのエンティティに必要な物とその実装
 *
 * カードにはアカウントがつくので、
 * それを加えたインターフェースもここに用意した
 */
interface Card {
    val id: String
    val name: String
    val phonetic: String
    val coverImageUrl: String
    val faceIconUrl: String
    val introduction: String

    data class Implement(
            override val id: String,
            override val name: String,
            override val phonetic: String,
            override val coverImageUrl: String,
            override val faceIconUrl: String,
            override val introduction: String
    ) : Card

    interface WithAccounts : Card {
        val accounts: List<Account>
    }

    interface Repository {

        companion object {
            val instance: Repository by lazy { Firebase() }
        }

        fun cardsOf(userId: Long): List<Card>
        fun partnerCardsOf(userIds: List<Long>): Map<Long, List<Card>>

        class Firebase : Repository {

            override fun cardsOf(userId: Long): List<Card> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun partnerCardsOf(userIds: List<Long>): Map<Long, List<Card>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }
}
