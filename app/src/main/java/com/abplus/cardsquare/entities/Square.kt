package com.abplus.cardsquare.entities

/**
 * スクエアのエンティティに必要な物とその実装
 */
interface Square {
    val id: Long
    val name: String
    val coverImageUrl: String

    data class Implement(
            val id: Long,
            val name: String,
            val coverImageUrl: String
    )
}
