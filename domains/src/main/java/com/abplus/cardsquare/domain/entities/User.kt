package com.abplus.cardsquare.domain.entities

import kotlinx.coroutines.Deferred

data class User(
        val refId: String = "",
        val defaultName: String = "",
        val accounts: List<Account> = ArrayList(),
        val cards: List<Card> = ArrayList()
) {
    companion object {
        private const val USERS = "users"
    }

    interface Repository {
        fun findAsync(uid: String): Deferred<User?>
        fun currentAsync(): Deferred<User?>
        fun addAsync(defaultName: String ): Deferred<User?>
    }
}

