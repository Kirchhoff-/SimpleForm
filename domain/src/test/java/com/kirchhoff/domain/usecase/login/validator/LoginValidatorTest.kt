package com.kirchhoff.domain.usecase.login.validator

import com.kirchhoff.domain.usecases.login.validator.LoginValidator
import com.kirchhoff.domain.utils.nextString
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.UUID
import kotlin.random.Random

class LoginValidatorTest {

    private lateinit var validator: LoginValidator

    @Before
    fun setup() {
        validator = LoginValidator()
    }

    @Test
    fun `verify validation of password`() {
        val notValidPassword = UUID.randomUUID().toString().substring(0, MIN_PASSWORD_LENGTH - 1)
        val validPassword = UUID.randomUUID().toString().substring(0, MIN_PASSWORD_LENGTH + 1)

        assertTrue(validator.isValidPassword(validPassword))
        assertFalse(validator.isValidPassword(notValidPassword))
    }

    @Test
    fun `verify email validation`() {
        //Here should be tests for check email
        //but I will write just simplified versions

        val notValidEmail = Random.nextString()
        val validEmail = "somemail@gmail.com"

        assertTrue(validator.isValidEmail(validEmail))
        assertFalse(validator.isValidEmail(notValidEmail))
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }
}