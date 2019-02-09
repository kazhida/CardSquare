package com.abplus.cardsquare.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abplus.cardsquare.app.ui.cardedit.CardEditActivity
import com.abplus.cardsquare.app.ui.cardlist.CardListActivity
import com.abplus.cardsquare.app.ui.userentry.UserEntryActivity
import com.abplus.cardsquare.app.utils.RandomImages
import com.abplus.cardsquare.di.UseCaseFactory
import com.abplus.cardsquare.domain.entities.Card
import com.abplus.cardsquare.domain.entities.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber.DebugTree
import timber.log.Timber


/**
 * MainActivityのやることは、
 * スプラッシュの表示と
 * 交通整理だけ。
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_ENTRY = 6592
        private const val REQUEST_CARD = 6593
        private const val REQUEST_LIST = 6594

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(DebugTree())
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch {
            val useCase = UseCaseFactory.createUserUseCase()
            val user = useCase.currentUser()
            val activity = this@MainActivity

            when {
                user == null            -> UserEntryActivity.start(activity, REQUEST_ENTRY)
                user.cards.isEmpty()    -> CardEditActivity.start(activity, initialCard(user), REQUEST_CARD)
                else                    -> CardListActivity.start(activity, user.cards, REQUEST_LIST)
            }
        }
    }

    private fun initialCard(holder: User): Card = Card(
            refId = "",
            userId = holder.refId,
            handleName = holder.defaultName,
            firstName = "John/Jane",
            familyName = "Doe",
            coverImageUrl = RandomImages.nextAssetImageUrl(),
            introduction = "ここでは、自己紹介文などを記入します。\nカードの左上に表示されます。",
            description = "ここには、なにを記入してもかまいません。\nカードの右下に表示されます。\nこのサービスでは住所はあつかっていないので、ここを使用するとよいでしょう。",
            accounts = listOf(holder.accounts.first()),
            partners = ArrayList()
    )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val canceled = resultCode == Activity.RESULT_CANCELED
        when (requestCode) {
            REQUEST_ENTRY -> if (canceled) finish()
            REQUEST_CARD -> if (canceled) finish()
            REQUEST_LIST -> finish()
        }
    }
}
