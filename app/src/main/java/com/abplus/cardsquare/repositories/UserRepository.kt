package com.abplus.cardsquare.repositories

import com.abplus.cardsquare.entities.User

interface UserRepository {

    companion object {
        val instance: UserRepository by lazy { Firebase() }
    }

    fun user(userId: Long): User

    class Firebase : UserRepository {

        override fun user(userId: Long): User {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
