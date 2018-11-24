package com.abplus.cardsquare.ui.common

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.abplus.cardsquare.domain.models.Account

class SquareCardViewModel(
        app: Application
) : AndroidViewModel(app) {

    var handleName = MutableLiveData<String>()
    var firstName = MutableLiveData<String>()
    var familyName = MutableLiveData<String>()
    var coverImageUrl = MutableLiveData<String>()
    var introduction = MutableLiveData<String>()
    var description = MutableLiveData<String>()

    var visibleHasGoogle = MutableLiveData<Int>()
    var visibleHasTwitter = MutableLiveData<Int>()
    var visibleHasFacebook = MutableLiveData<Int>()
    var visibleHasGitHub = MutableLiveData<Int>()

    private fun List<Account>.has(authProvider: String): Boolean {
        return find { it.provider == authProvider } != null
    }

    fun initAccountIcons(accounts: List<Account>) {
        visibleHasGoogle.value = if (accounts.has(Account.GOOGLE)) View.VISIBLE else View.GONE
        visibleHasTwitter.value = if (accounts.has(Account.TWITTER)) View.VISIBLE else View.GONE
        visibleHasFacebook.value = if (accounts.has(Account.FACEBOOK)) View.VISIBLE else View.GONE
        visibleHasGitHub.value = if (accounts.has(Account.GITHUB)) View.VISIBLE else View.GONE
    }
}