package com.abplus.cardsquare.ui.cardedit

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abplus.cardsquare.domain.models.Card

class CardEditViewModel(
        app: Application
) : AndroidViewModel(app) {

    private var isFirstCard: Boolean = false
    val handleName = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val familyName = MutableLiveData<String>()
    val coverImageUrl = MutableLiveData<String>()
    val introduction = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    fun reset(card: Card) {
        isFirstCard = card.refId.isEmpty()
        handleName.value = card.handleName
        firstName.value = card.firstName
        familyName.value = card.familyName
        coverImageUrl.value = card.coverImageUrl
        introduction.value = card.introduction
        description.value = card.description
    }

    val visibleAtFirst: Int
        get() = if (isFirstCard) View.VISIBLE else View.GONE
}
