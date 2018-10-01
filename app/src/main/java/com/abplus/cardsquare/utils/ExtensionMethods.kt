package com.abplus.cardsquare.utils

fun Boolean.ifTrue(proc: ()->Unit): Boolean = also {
    if (it) {
        proc()
    }
}