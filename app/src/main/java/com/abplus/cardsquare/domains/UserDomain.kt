package com.abplus.cardsquare.domains

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.abplus.cardsquare.entities.Account
import com.abplus.cardsquare.entities.Card
import com.abplus.cardsquare.entities.User
import com.abplus.cardsquare.utils.RandomImages
import com.abplus.cardsquare.utils.asyncFB
import kotlinx.coroutines.experimental.Deferred

/**
 * 現在サインインしているユーザ
 */
class UserDomain(
        private val userRepository: User.Repository = User.Repository.Firebase(),
        private val accountRepository: Account.Repository = Account.Repository.Firebase(),
        private val cardRepository: Card.Repository = Card.Repository.Firebase()
){
    suspend fun currentUser(): Deferred<User?> = asyncFB {
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
        userRepository.add(accountRepository.accountName)
    }

    suspend fun add(src: User): User? {
        return userRepository.add(src.defaultName).await()
    }

    suspend fun initialCard(): Card = currentUser().await()!!.let { user ->
        Card(
                refId = "",
                userId = user.refId,
                title = "1枚目のカード",
                name = user.defaultName,
                firstName = "John/Jane",
                familyName = "Doe",
                coverImageUrl = RandomImages.nextAssetImageUrl(),
                introduction = "ここでは、自己紹介文などを記入します。\nカードの右上に表示されます。",
                description = "ここには、なにを記入してもかまいません。\nカードの右下に表示されます。\nこのサービスでは住所は登録で来るようになっていないので、\n住所を明かす必要がある場合は、ここを使用してください。",
                accounts = user.accounts,
                partners = ArrayList()
        )
    }
}
