package com.abplus.cardsquare.entities

interface User {
    val uid: String
    val cards: List<Card.WithAccounts>
    val accounts: List<Account>

    interface Repository {
        fun user(userId: Long): User

        class Firebase : Repository {

            override fun user(userId: Long): User {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }
}