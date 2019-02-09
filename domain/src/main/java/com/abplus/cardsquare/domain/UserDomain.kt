package com.abplus.cardsquare.domain

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
class UserDomain(
        private val userRepository: User.Repository,
        private val accountRepository: Account.Repository,
        private val cardRepository: Card.Repository
){
    suspend fun currentUser(): Deferred<User?> = GlobalScope.async {
        userRepository.current().await()?.let {
            val accounts = accountRepository.findByUserId(it.refId).await()
            val cards = cardRepository.findCards(it.refId).await()

            it.copy(
                    accounts = accounts,
                    cards = cards
            )
        }
    }

    fun signIn(activity: FragmentActivity, requestCode: Int) {
        accountRepository.signIn(activity, requestCode)
    }

    fun onActivityResult(data: Intent?) {
        accountRepository.onActivityResult(data)
        GlobalScope.launch {
            userRepository.add(accountRepository.accountName).await()
        }
    }

    suspend fun add(src: User): User? {
        return userRepository.add(src.defaultName).await()
    }
}
