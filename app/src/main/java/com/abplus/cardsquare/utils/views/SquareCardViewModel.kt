package com.abplus.cardsquare.utils.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SquareCardViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val handleName = MutableLiveData<String>()
    val firstName = MutableLiveData<String>()
    val familyName = MutableLiveData<String>()
    val coverImageUrl = MutableLiveData<String>()
    val introduction = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    val hasGoogle = MutableLiveData<Int>()
    val hasTwitter = MutableLiveData<Int>()
    val hasFacebook = MutableLiveData<Int>()
    val hasGitHub = MutableLiveData<Int>()
}
