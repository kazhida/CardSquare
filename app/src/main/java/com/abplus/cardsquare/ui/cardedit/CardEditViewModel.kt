package com.abplus.cardsquare.ui.cardedit

import android.view.View
import androidx.lifecycle.ViewModel
import com.abplus.cardsquare.domain.models.Account
import com.abplus.cardsquare.domain.models.Card
import com.abplus.cardsquare.utils.views.SquareCardViewModel

class CardEditViewModel : ViewModel() {

    private val card = SquareCardViewModel()
    private var isFirstCard: Boolean = false
    private val accounts = ArrayList<Account>()

    fun reset(card: Card) {
        isFirstCard = card.refId.isEmpty()

        handleName = card.handleName
        firstName = card.firstName
        familyName = card.familyName
        coverImageUrl = card.coverImageUrl
        introduction = card.introduction
        description = card.description
        accounts.addAll(accounts.map { it.copy() })
    }

    val visibleAtFirst: Int
        get() = if (isFirstCard) View.VISIBLE else View.GONE

    var handleName: String
        get() = card.handleName.value ?: ""
        set(value) {
            card.handleName.postValue(value)
        }

    var firstName: String
        get() = card.firstName.value ?: ""
        set(value) {
            card.firstName.postValue(value)
        }

    var familyName: String
        get() = card.familyName.value ?: ""
        set(value) {
            card.familyName.postValue(value)
        }

    var coverImageUrl: String
        get() = card.coverImageUrl.value ?: ""
        set(value) {
            card.coverImageUrl.postValue(value)
        }

    var introduction: String
        get() = card.introduction.value ?: ""
        set(value) {card.introduction.postValue(value)}

    var description: String
        get() = card.description.value ?: ""
        set(value) {card.description.postValue(value)}
}