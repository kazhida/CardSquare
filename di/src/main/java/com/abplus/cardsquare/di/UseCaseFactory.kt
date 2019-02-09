package com.abplus.cardsquare.di

import com.abplus.cardsquare.datastore.AccountRepository
import com.abplus.cardsquare.datastore.CardRepository
import com.abplus.cardsquare.datastore.UserRepository
import com.abplus.cardsquare.domain.usecases.UserUseCase

object UseCaseFactory {

    fun createUserUseCase(): UserUseCase = UserUseCase(
            UserRepository(),
            AccountRepository(),
            CardRepository()
    )
}