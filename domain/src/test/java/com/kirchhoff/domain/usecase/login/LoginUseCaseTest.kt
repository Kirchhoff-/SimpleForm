package com.kirchhoff.domain.usecase.login

import com.kirchhoff.core.Result
import com.kirchhoff.domain.exceptions.IExceptionConverter
import com.kirchhoff.domain.models.LoginError
import com.kirchhoff.domain.models.LoginResult
import com.kirchhoff.domain.models.User
import com.kirchhoff.domain.repository.ILoginRepository
import com.kirchhoff.domain.usecases.login.LoginUseCase
import com.kirchhoff.domain.usecases.login.validator.ILoginValidator
import com.kirchhoff.domain.utils.CoroutineRule
import com.kirchhoff.domain.utils.nextString
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations
import kotlin.random.Random

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    private lateinit var useCase: LoginUseCase

    @Mock
    private lateinit var repository: ILoginRepository

    @Mock
    private lateinit var validator: ILoginValidator

    @Mock
    private lateinit var exceptionConverter: IExceptionConverter<LoginError>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        useCase = LoginUseCase(repository, validator, exceptionConverter)
    }

    @Test
    fun `verify login with not valid email`() = runBlockingTest {
        val email = Random.nextString()
        val password = Random.nextString()
        `when`(validator.isValidEmail(email)).then { false }

        val result = useCase.login(email, password)

        assertNull(result.user)
        assertNotNull(result.error)
        assertTrue(result.error is LoginError.InvalidEmail)
        verify(validator).isValidEmail(email)
        verifyNoMoreInteractions(repository)
        verifyNoInteractions(exceptionConverter)
    }

    @Test
    fun `verify login with not valid password`() = runBlockingTest {
        val email = Random.nextString()
        val password = Random.nextString()
        `when`(validator.isValidEmail(email)).then { true }
        `when`(validator.isValidPassword(password)).then { false }

        val result = useCase.login(email, password)

        assertNull(result.user)
        assertNotNull(result.error)
        assertTrue(result.error is LoginError.InvalidPassword)
        verify(validator).isValidEmail(email)
        verify(validator).isValidPassword(password)
        verifyNoMoreInteractions(repository)
        verifyNoInteractions(exceptionConverter)
    }

    @Test
    fun `verify exception during login`() = runBlockingTest {
        val email = Random.nextString()
        val password = Random.nextString()
        val exceptionMessage = Random.nextString()
        val exception = Exception(exceptionMessage)
        val loginError = LoginError.Other(exceptionMessage)
        `when`(validator.isValidEmail(email)).then { true }
        `when`(validator.isValidPassword(password)).then { true }
        `when`(repository.login(email, password)).then { Result.Exception<LoginResult>(exception) }
        `when`(exceptionConverter.convert(exception)).then { loginError }

        val result = useCase.login(email, password)

        assertNull(result.user)
        assertNotNull(result.error)
        assertTrue(result.error is LoginError.Other)
        val resultError = result.error as LoginError.Other
        assertEquals(resultError.message, exceptionMessage)
        verify(validator).isValidEmail(email)
        verify(validator).isValidPassword(password)
        verify(repository).login(email, password)
        verify(exceptionConverter).convert(exception)
    }

    @Test
    fun `verify success login`() = runBlockingTest {
        val email = Random.nextString()
        val password = Random.nextString()
        val firstName = Random.nextString()
        val lastName = Random.nextString()
        val info = Random.nextString()
        val resultUser = User(firstName, lastName, info, email)
        `when`(validator.isValidEmail(email)).then { true }
        `when`(validator.isValidPassword(password)).then { true }
        `when`(repository.login(email, password)).then { Result.Success(resultUser) }

        val result = useCase.login(email, password)

        assertNotNull(result.user)
        assertNull(result.error)
        assertEquals(result.user, resultUser)
        verify(validator).isValidEmail(email)
        verify(validator).isValidPassword(password)
        verify(repository).login(email, password)
        verifyNoInteractions(exceptionConverter)
    }
}
