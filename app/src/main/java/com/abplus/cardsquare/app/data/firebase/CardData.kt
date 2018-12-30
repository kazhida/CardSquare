package com.abplus.cardsquare.app.data.firebase

class CardData(
        val refId: String = "",
        val userId: String = "",
        val title: String = "",
        val name: String = "",
        val firstName: String = "",
        val familyName: String = "",
        val coverImageUrl: String = "",
        val introduction: String = "",
        val description: String = "",
        val accounts: List<String> = ArrayList()
)
