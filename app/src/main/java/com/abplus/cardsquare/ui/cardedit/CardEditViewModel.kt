package com.abplus.cardsquare.ui.cardedit

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abplus.cardsquare.domain.models.Card
import com.abplus.cardsquare.utils.views.SquareCardViewModel

class CardEditViewModel(
        context: Application
) : AndroidViewModel(context) {
    private var isFirstCard: Boolean = false
    var cardViewModel = SquareCardViewModel(context)
    var handleName = MutableLiveData<String>()
    var firstName = MutableLiveData<String>()
    var familyName = MutableLiveData<String>()
    var coverImageUrl = MutableLiveData<String>()
    var introduction = MutableLiveData<String>()
    var description = MutableLiveData<String>()

    fun reset(card: Card) {
        isFirstCard = card.refId.isEmpty()
        handleName.value = card.handleName
        firstName.value = card.firstName
        familyName.value = card.familyName
        coverImageUrl.value = card.coverImageUrl
        introduction.value = card.introduction
        description.value = card.description
        cardViewModel.initAccountIcons(card.accounts)
        cardViewModel.coverImageUrl.value = card.coverImageUrl
    }

    val visibleAtFirst: Int
        get() = if (isFirstCard) View.VISIBLE else View.GONE
}