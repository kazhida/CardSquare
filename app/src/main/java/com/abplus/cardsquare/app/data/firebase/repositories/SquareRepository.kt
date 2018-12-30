package com.abplus.cardsquare.app.data.firebase.repositories

import com.abplus.cardsquare.app.domain.models.Square
import kotlinx.coroutines.Deferred

class SquareRepository : FirebaseRepository(), Square.Repository {

    companion object {
        private const val SQUARES = "SQUARES"
    }

    override fun all(): Deferred<List<Square>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(name: String, coverImageUrl: String): Deferred<Square> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}