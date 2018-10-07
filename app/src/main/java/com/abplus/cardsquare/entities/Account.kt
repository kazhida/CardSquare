package com.abplus.cardsquare.entities

import org.parceler.Parcel

/**
 * カードに付加するSNSなどのアカウントのエンティティ
 */
@Parcel
class Account private constructor(
        val refId: String,
        val uid: String,
        val type: Type,
        val name: String,
        val email: String = "",
        val id: Long = 0,
        val idAsString: String = ""
) {
    // for parceler
    @Suppress("unused")
    private constructor() : this("", "", Type.Unknown, "")

    enum class Type {
        Unknown,
        Google,
        Twitter,
        Facebook,
        GitHub
    }

    companion object {

        const val GOOGLE = "google"
        fun google(
                refId: String,
                uid: String,
                name: String,
                email: String
        ) = Account(
                refId = refId,
                uid = uid,
                type = Type.Google,
                name = name,
                email = email
        )

        const val TWITTER = "twitter"
        fun twitter(
                refId: String,
                uid: String,
                name: String,
                id: Long,
                idAsString: String
        ) = Account(
                refId = refId,
                uid = uid,
                type = Type.Twitter,
                name = name,
                id = id,
                idAsString = idAsString
        )

        const val FACEBOOK = "facebook"
        fun facebook(
                refId: String,
                uid: String,
                name: String
        ) = Account(refId, uid, Type.Facebook, name)

        const val GITHUB = "github"
        fun github(
                refId: String,
                uid: String,
                name: String
        ) = Account(refId, uid, Type.GitHub, name)
    }
}
