package com.marvel.marveluniverse.utils

import org.hamcrest.Matchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AuthorizationTextValidatorTest {

    private lateinit var authorizationTextValidator: AuthorizationTextValidator

    @Before
    fun setup() {
        authorizationTextValidator = AuthorizationTextValidator()
    }

    @Test
    fun isValidByLength() {

        val result1 = authorizationTextValidator.isValidByLength("User")

        assertThat(result1, `is` (false))

        val result2 = authorizationTextValidator.isValidByLength("User1")

        assertThat(result2, `is` (true))

        val result3 = authorizationTextValidator.isValidByLength("")

        assertThat(result3, `is` (false))
    }

    @Test
    fun isValidInput() {

        val result1 = authorizationTextValidator.isValidInput(" ")
        val result2 = authorizationTextValidator.isValidInput("_")
        val result3 = authorizationTextValidator.isValidInput("=")
        val result4 = authorizationTextValidator.isValidInput("#")
        val result5 = authorizationTextValidator.isValidInput("User%")
        val result6 = authorizationTextValidator.isValidInput(" User4")
        val result7 = authorizationTextValidator.isValidInput("UserOne")
        val result8 = authorizationTextValidator.isValidInput("456329891")

        assertThat(result1, `is` (false))
        assertThat(result2, `is` (false))
        assertThat(result3, `is` (false))
        assertThat(result4, `is` (false))
        assertThat(result5, `is` (false))
        assertThat(result6, `is` (false))
        assertThat(result7, `is` (true))
        assertThat(result8, `is` (true))
    }
}