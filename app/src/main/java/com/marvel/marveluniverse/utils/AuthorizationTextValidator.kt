package com.marvel.marveluniverse.utils

private const val MIN_LENGTH = 5

class AuthorizationTextValidator {

    fun isValidByLength(text: String): Boolean {
        var valid = true

        if (text.length < MIN_LENGTH) {
            valid = false
        }

        return valid
    }

    fun isValidInput(text: String) = text.all {
        it in ('a'..'z') || it in ('A'..'Z') || it in ('0'..'9')
    }
}
