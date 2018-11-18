package com.abplus.cardsquare.domain.usecases

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.abplus.cardsquare.domain.models.Account
import com.abplus.cardsquare.domain.models.Card
import com.abplus.cardsquare.data.firebase.repositories.AccountRepository
import com.abplus.cardsquare.data.firebase.repositories.CardRepository
import com.abplus.cardsquare.data.firebase.repositories.HolderRepository
import com.abplus.cardsquare.domain.models.Holder
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class HolderUseCase(
        private val accountRepository: Account.Repository = AccountRepository(),
        private val cardRepository: Card.Repository = CardRepository(),
        private val holderRepository: Holder.Repository = HolderRepository()
) {

    val currentHolder: Deferred<Holder?> get() = holderRepository.current

    fun loadCards(userId: String): List<Card> = ArrayList<Card>().apply {

    }

    fun signIn(activity: FragmentActivity, requestCode: Int, onFailed: (errorMessage: String)->Unit) {
        holderRepository.signIn(activity, requestCode, onFailed)
    }

    fun onActivityResult(data: Intent?): Deferred<Holder?> = GlobalScope.async {
        val uid = accountRepository.saved(data).await()
        val account = accountRepository.find(uid).await()
        if (account != null) {
            holderRepository.create(account).await()
        } else {
            null
        }
    }

//
//    private fun List<Card>.append(card: Card) = ArrayList<Card>().apply {
//        addAll(this@append)
//        add(card)
//    }
//
//    private fun Map<String, Account>.append(account: Account) = HashMap<String, Account>().apply {
//        putAll(this@append)
//        put(account.uid, account)
//    }
}
