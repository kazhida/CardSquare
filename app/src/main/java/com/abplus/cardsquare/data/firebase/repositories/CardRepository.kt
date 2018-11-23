package com.abplus.cardsquare.data.firebase.repositories

import com.abplus.cardsquare.domain.models.Account
import com.abplus.cardsquare.domain.models.Card
import kotlinx.coroutines.Deferred

class CardRepository : FirebaseRepository(), Card.Repository {

    companion object {
        private const val CARDS = "CARDS"
    }

    override fun Card.save(): Deferred<Card> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByRefIds(refIds: List<String>): Deferred<List<Card>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findByUserId(userId: String): Deferred<List<Card>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(userId: String, title: String, name: String, firstName: String, familyName: String, coverImageUrl: String, introduction: String, description: String, accounts: List<Account>): Deferred<Card> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}