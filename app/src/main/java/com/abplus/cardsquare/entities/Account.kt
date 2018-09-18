package com.abplus.cardsquare.entities

/**
 * カードに付加するSNSなどのアカウントのエンティティに必要な物と実装
 *
 * アカウントを束ねるインターフェースもここに用意した
 */
interface Account {
    val id: Long
    val type: Type
    val name: String
    val ownerId: Long

    enum class Type {
        Google,
        Twitter,
        Facebook
    }

    data class Implement(
            override val id: Long,
            override val type: Type,
            override val name: String,
            override val ownerId: Long
    ) : Account

    interface Holder {
        val accounts: List<Account>
    }
}
