package com.abplus.cardsquare.data.firebase

data class AccountData(
        val refId: String,
        val uid: String,
        val provider: String,
        val name: String = ""
        // todo: リアクションのための情報が必要
)
