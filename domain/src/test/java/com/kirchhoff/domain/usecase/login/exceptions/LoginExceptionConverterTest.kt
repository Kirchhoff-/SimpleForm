package com.kirchhoff.domain.usecase.login.exceptions

import com.kirchhoff.domain.exceptions.exceptions.LoginException
import com.kirchhoff.domain.models.LoginError
import com.kirchhoff.domain.usecases.login.exceptions.LoginExceptionConverter
import com.kirchhoff.domain.utils.nextString
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class LoginExceptionConverterTest {

    private lateinit var converter: LoginExceptionConverter

    @Before
    fun setup() {
        converter = LoginExceptionConverter()
    }

    @Test
    fun `verify converting login exception to server error`() {
        val errorMessage = Random.nextString()
        val errorCode = Random.nextInt(SERVER_ERROR_MAX_CODE)
        val loginException = LoginException(errorMessage, errorCode)

        val resultError = converter.convert(loginException)

        assertTrue(resultError is LoginError.ServerError)
        assertEquals((resultError as LoginError.ServerError).message, errorCode.toString())
    }

    @Test
    fun `verify converting login exception to network error`() {
        val errorMessage = Random.nextString()
        val errorCode = Random.nextInt(NETWORK_ERROR_MIN_CODE, NETWORK_ERROR_MAX_CODE)
        val loginException = LoginException(errorMessage, errorCode)

        val resultError = converter.convert(loginException)

        assertTrue(resultError is LoginError.NetworkError)
        assertEquals((resultError as LoginError.NetworkError).message, errorMessage)
    }

    @Test
    fun `verify converting login exception to other error`() {
        val errorMessage = Random.nextString()
        val errorCode = Random.nextInt(OTHER_ERROR_MIN_CODE, Int.MAX_VALUE)
        val loginException = LoginException(errorMessage, errorCode)

        val resultError = converter.convert(loginException)

        assertTrue(resultError is LoginError.Other)
        assertEquals((resultError as LoginError.Other).message, "$errorCode $errorMessage")
    }

    @Test
    fun `verify converting exception to other error`() {
        val exceptionMessage = Random.nextString()
        val exception = IllegalArgumentException(exceptionMessage)

        val resultError = converter.convert(exception)

        assertTrue(resultError is LoginError.Other)
        assertEquals((resultError as LoginError.Other).message, exceptionMessage)
    }

    companion object {
        private const val SERVER_ERROR_MAX_CODE = 100
        private const val NETWORK_ERROR_MIN_CODE = 101
        private const val NETWORK_ERROR_MAX_CODE = 200
        private const val OTHER_ERROR_MIN_CODE = 201
    }
}
