package com.abplus.cardsquare.domain.usecases

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.abplus.cardsquare.domain.entities.Account
import com.abplus.cardsquare.domain.entities.Card
import com.abplus.cardsquare.domain.entities.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * 現在サインインしているユーザ
 */
class UserUseCase(
        private val userRepository: User.Repository,
        private val accountRepository: Account.Repository,
        private val cardRepository: Card.Repository
){
    suspend fun currentUser(): User? = GlobalScope.async {
        userRepository.currentAsync().await()?.let {
            val accounts = accountRepository.findByUserIdAsync(it.refId).await()
            val cards = cardRepository.findCardsAsync(it.refId).await()
            it.copy(
                    accounts = accounts,
                    cards = cards
            )
        }
    }.await()

    fun signIn(activity: FragmentActivity, idToken: String, requestCode: Int) {
        accountRepository.signIn(activity, idToken, requestCode)
    }

    fun onActivityResult(data: Intent?) {
        accountRepository.onActivityResult(data)
        GlobalScope.launch {
            userRepository.addAsync(accountRepository.accountName).await()
        }
    }

    suspend fun add(src: User): User? {
        return userRepository.addAsync(src.defaultName).await()
    }
}
