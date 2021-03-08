package com.kirchhoff.repository

import com.kirchhoff.core.IMapper
import com.kirchhoff.core.Result
import com.kirchhoff.data.models.NetworkUser
import com.kirchhoff.domain.exceptions.exceptions.LoginException
import com.kirchhoff.domain.models.User
import com.kirchhoff.service.IUserService
import com.kirchhoff.utils.nextString
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {

    private lateinit var repository: LoginRepository

    @Mock
    private lateinit var service: IUserService

    @Mock
    private lateinit var mapper: IMapper<NetworkUser, User>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        repository = LoginRepository(service, mapper)
    }

    @Test
    fun `verify exception during login`() = runBlockingTest {
        val email = Random.nextString()
        val password = Random.nextString()
        val errorMessage = Random.nextString()
        `when`(service.login(email, password)).then { error(errorMessage) }

        val result = repository.login(email, password)

        assertTrue(result is Result.Exception)
        assertTrue((result as Result.Exception).exception is LoginException)
        val loginException = (result.exception as LoginException)
        assertEquals(loginException.errorMessage, errorMessage)
        assertTrue(loginException.errorCode < 256)
        verify(service).login(email, password)
        verifyNoMoreInteractions(service)
        verifyNoInteractions(mapper)
    }

    @Test
    fun `verify success user login`() = runBlockingTest {
        val email = Random.nextString()
        val password = Random.nextString()
        val firstName = Random.nextString()
        val lastName = Random.nextString()
        val info = Random.nextString()
        val networkUser = NetworkUser(firstName, lastName, info, email)
        `when`(service.login(email, password)).then { networkUser }
        `when`(mapper.map(networkUser)).then { User(firstName, lastName, info, email) }

        val result = repository.login(email, password)

        assertTrue(result is Result.Success)
        val user = (result as Result.Success).data
        assertEquals(firstName, user.firstName)
        assertEquals(lastName, user.lastName)
        assertEquals(info, user.info)
        assertEquals(email, user.email)
        verify(service).login(email, password)
        verify(mapper).map(networkUser)
        verifyNoMoreInteractions(service)
        verifyNoMoreInteractions(mapper)
    }
}