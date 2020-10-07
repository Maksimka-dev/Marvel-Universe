package com.marvel.marveluniverse

import io.reactivex.Observable

fun main() {

    val r = Observable.just("1", "22", "333", "4444", "55555")
        .groupBy {
            it.length > 3
        }
        .subscribe {

        }
}
