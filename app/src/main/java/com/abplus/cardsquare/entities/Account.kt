package com.abplus.cardsquare.entities

/**
 * カードに付加するSNSなどのアカウントのエンティティに必要な物と実装
 *
 * アカウントを束ねるインターフェースもここに用意した
 */
interface Account {
    val id: String
    val type: Type
    val name: String
    val ownerId: String

    enum class Type {
        Google,
        Twitter,
        Facebook
    }

    data class Implement(
            override val id: String,
            override val type: Type,
            override val name: String,
            override val ownerId: String
    ) : Account

    interface Holder {
        val accounts: List<Account>
    }

    interface Repository {

        companion object {
            val instance: Repository by lazy { Firebase() }
        }

        fun usable() : Account.Holder
        fun usable(userId: String) : Account.Holder

        class Firebase : Repository {

            override fun usable(): Account.Holder {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun usable(userId: String): Account.Holder {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        class Mock : Repository {

            override fun usable(): Holder {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun usable(userId: String): Holder {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }
}
