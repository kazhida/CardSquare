package com.abplus.cardsquare.entities

interface User {
    val cards: List<Card.WithAccounts>
    val accounts: List<Account>
}