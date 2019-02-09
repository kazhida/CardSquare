package com.abplus.cardsquare.datastore

/**
 * スクエアのエンティティに必要な物とその実装
 */
interface Square {
    val ref: String
    val name: String
    val coverImageUrl: String

    data class Implement(
            override val ref: String,
            override val name: String,
            override val coverImageUrl: String
    ) : Square

    interface Repository {

    }
}
