package com.abplus.cardsquare.app.utils

@Suppress("unused")
class IdOr<T> private constructor(
        val id: String?,
        val data: T?
) {
    constructor(id: String) : this(id, null)
    constructor(data: T) : this(null, data)

    val isId: Boolean get() = data == null
    val hasData: Boolean get() = data != null
}

@Suppress("unused")
typealias RefOr<T> = IdOr<T>
