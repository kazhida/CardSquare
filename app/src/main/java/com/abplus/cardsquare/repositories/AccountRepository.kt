package com.abplus.cardsquare.repositories

import com.abplus.cardsquare.entities.Account


interface AccountRepository {

    companion object {
        val instance: AccountRepository by lazy { Firebase() }
    }

    fun usable() : Account.Holder
    fun usable(userId: Long) : Account.Holder

    class Firebase : AccountRepository {

        override fun usable(): Account.Holder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun usable(userId: Long): Account.Holder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}