package com.abplus.cardsquare.data.firebase.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Firebaseを使う際にあると便利な諸々を実装した抽象クラス
 *
 * 「継承よりコンポジション」といわれているが、
 * このパッケージ内のクラスには必須だし、
 * このパッケージ内に隠蔽したいという理由から
 * 継承させることにした。
 */
abstract class FirebaseRepository {

    protected val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    protected val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    protected fun DocumentSnapshot.getStringOrEmpty(field: String): String = getString(field) ?: ""
    protected fun DocumentSnapshot.getLongOrZero(field: String): Long = getLong(field) ?: 0
    protected val DocumentSnapshot.refId: String get() = reference.id

    protected fun <T> Task<T>.defer(): Deferred<Task<T>> = GlobalScope.async {
        suspendCoroutine<Task<T>> { continuation ->
            this@defer.apply {
                addOnCompleteListener {
                    task -> continuation.resume(task)
                }
                addOnFailureListener {
                    task -> continuation.resumeWithException(task)
                }
            }
        }
    }

    protected fun Query.defer(): Deferred<QuerySnapshot> = GlobalScope.async {
        suspendCoroutine<QuerySnapshot> { continuation ->
            this@defer.addSnapshotListener { snapshot, exception ->
                when {
                    exception != null -> continuation.resumeWithException(exception)
                    snapshot != null -> continuation.resume(snapshot)
                }
            }
        }
    }

    protected fun <T, R> Task<T>.onSuccess(proc: (T) -> R): R? {
        return if (isSuccessful) {
            result?.let(proc)
        } else {
            null
        }
    }

//    class HolderRepository : Account.Repository {
//
//        override fun signIn(activity: AppCompatActivity, requestCode: Int) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun onActivityResult(data: Intent?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//    }
//
//    class HolderRepository : HolderData.Repository {
//
//        override suspend fun findCards(userId: String): Deferred<List<Card>> {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override suspend fun add(
//                userId: String,
//                title: String,
//                handleName: String,
//                firstName: String,
//                familyName: String,
//                coverImageUrl: String,
//                introduction: String,
//                description: String,
//                accounts: List<Account>,
//                partners: List<Card>
//        ): Deferred<Card?> = asyncFB {
//            val data = mapOf(
//                    "userId" to userId,
//                    "title" to title,
//                    "handleName" to handleName,
//                    "firstName" to firstName,
//                    "familyName" to familyName,
//                    "coverImageUrl" to coverImageUrl,
//                    "introduction" to introduction,
//                    "description" to description,
//                    "accounts" to accounts,
//                    "partners" to partners
//            )
//            val task = store.collection(CARDS)
//                    .add(data)
//                    .defer()
//                    .await()
//            if (task.isSuccessful) {
//                task.result?.let { ref ->
//                    Card(
//                            refId = ref.id,
//                            userId = userId,
//                            title = title,
//                            handleName = handleName,
//                            firstName = firstName,
//                            familyName = familyName,
//                            coverImageUrl = coverImageUrl,
//                            introduction = introduction,
//                            description = description
//                    )
//                }
//            } else {
//                null
//            }
//
//        }
//
//        override fun find(accountRefId: String): HolderData {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun create(account: Account): HolderData {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun saved(): HolderData {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun add(card: Card): HolderData {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//        override fun add(account: Account): HolderData {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//    }
}