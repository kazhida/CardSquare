package com.abplus.cardsquare.entities

/**
 * カードに付加するSNSなどのアカウントのエンティティ
 */
sealed class Account(
        val ref: String,
        val uid: String,
        val name: String
) {
    class Google(
            ref: String,
            uid: String,
            name: String
    ) : Account(ref, uid, name)

    class Twitter(
            ref: String,
            uid: String,
            name: String,
            val id: Long,
            val idAsString: String
    ) : Account(ref, uid, name)

    class Facebook(
            ref: String,
            uid: String,
            name: String
    ) : Account(ref, uid, name)

    class GitHub(
            ref: String,
            uid: String,
            name: String
    ) : Account(ref, uid, name)

    interface Repository {

        fun all(): List<Account>
        fun add(account: Account): String
        fun remove(ref: String): Boolean

        companion object {
            val instance: Repository by lazy { Mock() }
        }

        class Firebase(private val uid: String) : Repository {
            override fun all(): List<Account> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun add(account: Account): String {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun remove(ref: String): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }

        class Mock : Repository {

            private val uid = "uid"
            private val email = "jdoe@gmail.com"
            private val name = "Jane Doe"

            private val accounts = ArrayList<Account>().apply {
                add(Google("1", uid, email))
                add(Facebook("2", uid, email))
                add(Twitter("3", uid, name, 3, "3"))
                add(GitHub("3", uid, name))
            }

            override fun all(): List<Account> = accounts

            override fun add(account: Account): String {
                remove(account.ref)
                accounts.add(account)
                return account.ref
            }

            override fun remove(ref: String): Boolean {
                val a = accounts.firstOrNull { it.ref == ref }
                return if (a != null) {
                    accounts.remove(a)
                    true
                } else {
                    false
                }
            }
        }
    }
}
