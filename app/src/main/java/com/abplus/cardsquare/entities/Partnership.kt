package com.abplus.cardsquare.entities

/**
 * ユーザ間の繋がりのエンティティに必要な物とその実装
 *
 * これはだいたいドメインでのみ扱うことになるはず。
 */
interface Partnership {
    val ownerId: Long
    val cardId: Long
    val partnerId: Long

    data class Implement(
            override val ownerId: Long,
            override val cardId: Long,
            override val partnerId: Long
    ) : Partnership
}
