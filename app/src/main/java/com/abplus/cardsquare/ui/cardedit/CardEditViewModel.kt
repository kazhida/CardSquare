package com.abplus.cardsquare.ui.cardedit

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abplus.cardsquare.domain.models.Card

class CardEditViewModel(
        app: Application
) : AndroidViewModel(app) {

    val handleName = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val familyName = MutableLiveData<String>()
    val coverImageUrl = MutableLiveData<String>()
    val introduction = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val visibleAtFirst = MutableLiveData<Int>()

    fun reset(card: Card) {
        handleName.value = card.handleName
        firstName.value = card.firstName
        familyName.value = card.familyName
        coverImageUrl.value = card.coverImageUrl
        introduction.value = card.introduction
        description.value = card.description
        visibleAtFirst.value = if (card.refId.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
