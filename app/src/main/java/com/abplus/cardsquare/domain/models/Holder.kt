package com.abplus.cardsquare.domain.models

import android.os.Parcelable
import androidx.fragment.app.FragmentActivity
import com.abplus.cardsquare.utils.RandomImages
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Deferred

/**
 * ユーザ
 */
@Parcelize
data class Holder(
        val refId: String = "",
        val defaultName: String = "",
        val accounts: Map<String, Account> = HashMap(),
        val cards: List<Card> = ArrayList()
) : Parcelable {

    private fun Map<String, Account>.append(accounts: Map<String, Account>) = HashMap<String, Account>().apply {
        putAll(this@append)
        putAll(accounts)
    }

    @Suppress("unused")
    private fun List<Card>.append(cards: List<Card>) = ArrayList<Card>().apply {
        addAll(this@append)
        addAll(cards)
    }

    fun add(accounts: Map<String, Account>): Holder = copy(accounts = accounts.append(accounts))

    fun initialCard(): Card = Card(
            refId = "",
            userId = refId,
            handleName = defaultName,
            firstName = "John/Jane",
            familyName = "Doe",
            coverImageUrl = RandomImages.nextAssetImageUrl(),
            introduction = "ここでは、自己紹介文などを記入します。\nカードの右上に表示されます。",
            description = "ここには、なにを記入してもかまいません。\nカードの右下に表示されます。\nこのサービスでは住所は登録で来るようになっていないので、\n住所を明かす必要がある場合は、ここを使用してください。",
            accounts = listOf(accounts.entries.first().value),
            partners = ArrayList()
    )

    interface Repository {
        fun signIn(activity: FragmentActivity, requestCode: Int, onFailed: (errorMessage: String)->Unit)
        val current: Deferred<Holder?>
        fun findByAccountId(accountId: String): Deferred<Holder?>
        fun create(account: Account): Deferred<Holder?>
    }
}
