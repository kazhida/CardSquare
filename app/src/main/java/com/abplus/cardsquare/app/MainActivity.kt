package com.abplus.cardsquare.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abplus.cardsquare.app.data.firebase.repositories.AccountRepository
import com.abplus.cardsquare.app.data.firebase.repositories.HolderRepository
import com.abplus.cardsquare.app.ui.cardedit.CardEditActivity
import com.abplus.cardsquare.app.ui.cardlist.CardListActivity
import com.abplus.cardsquare.app.ui.userentry.UserEntryActivity
import com.abplus.cardsquare.app.utils.RandomImages
import com.abplus.cardsquare.app.utils.launchUI
import com.abplus.cardsquare.domain.models.Card
import com.abplus.cardsquare.domain.models.Holder
import com.abplus.cardsquare.domain.usecases.HolderUseCase


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

        fun createHolderUseCase() = HolderUseCase(
                AccountRepository(),
                //CardRepository(),
                HolderRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        launchUI {
            val useCase = createHolderUseCase()
            val holder = useCase.currentHolder.await()

            when {
                holder == null -> UserEntryActivity.start(this, REQUEST_ENTRY)
                holder.cards.isEmpty() -> CardEditActivity.start(this, initialCard(holder), REQUEST_CARD)
                else -> CardListActivity.start(this, holder.cards, REQUEST_LIST)
            }
        }
    }

    private fun initialCard(holder: Holder): Card = Card(
            refId = "",
            userId = holder.refId,
            handleName = holder.defaultName,
            firstName = "John/Jane",
            familyName = "Doe",
            coverImageUrl = RandomImages.nextAssetImageUrl(),
            introduction = "ここでは、自己紹介文などを記入します。\nカードの左上に表示されます。",
            description = "ここには、なにを記入してもかまいません。\nカードの右下に表示されます。\nこのサービスでは住所はあつかっていないので、ここを使用するとよいでしょう。",
            accounts = listOf(holder.accounts.entries.first().value),
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
