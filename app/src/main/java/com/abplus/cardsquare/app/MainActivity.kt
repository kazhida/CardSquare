package com.abplus.cardsquare.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abplus.cardsquare.app.R
import com.abplus.cardsquare.app.domain.usecases.HolderUseCase
import com.abplus.cardsquare.app.ui.cardedit.CardEditActivity
import com.abplus.cardsquare.app.ui.cardlist.CardListActivity
import com.abplus.cardsquare.app.ui.userentry.UserEntryActivity
import com.abplus.cardsquare.app.utils.launchUI


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
    }

    override fun onResume() {
        super.onResume()

        launchUI {
            val useCase = HolderUseCase.firebaseInstance()
            val holder = useCase.currentHolder.await()

            when {
                holder == null -> UserEntryActivity.start(this, REQUEST_ENTRY)
                holder.cards.isEmpty() -> CardEditActivity.start(this, holder.initialCard(), REQUEST_CARD)
                else -> CardListActivity.start(this, holder.cards, REQUEST_LIST)
            }
        }
    }

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
