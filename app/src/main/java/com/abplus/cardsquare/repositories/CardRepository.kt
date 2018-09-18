package com.abplus.cardsquare.repositories

import com.abplus.cardsquare.entities.Card

/**
 * Cardの永続性を管理するクラス
 */
interface CardRepository {

    companion object {
        val instance: CardRepository by lazy { Firebase() }
    }

    fun cardsOf(userId: Long): List<Card>
    fun partnerCardsOf(userIds: List<Long>): Map<Long, List<Card>>

    class Firebase : CardRepository {

        override fun cardsOf(userId: Long): List<Card> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun partnerCardsOf(userIds: List<Long>): Map<Long, List<Card>> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}