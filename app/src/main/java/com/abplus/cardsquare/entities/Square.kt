package com.abplus.cardsquare.entities

/**
 * スクエアのエンティティに必要な物とその実装
 */
interface Square {
    val id: String
    val name: String
    val coverImageUrl: String

    data class Implement(
            override val id: String,
            override val name: String,
            override val coverImageUrl: String
    ) : Square

    interface Repository {

    }
}
