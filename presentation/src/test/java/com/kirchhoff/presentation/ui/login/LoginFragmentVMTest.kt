package com.kirchhoff.presentation.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kirchhoff.domain.models.LoginError
import com.kirchhoff.domain.models.LoginResult
import com.kirchhoff.domain.models.User
import com.kirchhoff.domain.usecases.login.ILoginUseCase
import com.kirchhoff.presentation.utils.CoroutineRule
import com.kirchhoff.presentation.utils.TestDispatchers
import com.kirchhoff.presentation.utils.nextString
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginFragmentVMTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    private lateinit var vm: LoginFragmentVM

    private val useCase = mock(ILoginUseCase::class.java)
    private val dispatchers = TestDispatchers()

    @Before
    fun setup() {
        vm = LoginFragmentVM(useCase, dispatchers)
    }

    @Test
    fun `verify init view state`() {
        verifyViewState(initialViewState)
    }

    @Test
    fun `verify entered same email`() {
        vm.onEmailChanged("")

        verifyViewState(initialViewState)
    }

    @Test
    fun `verify entered new mail`() {
        val email = Random.nextString(10)
        vm.onEmailChanged(email)

        verifyViewState(initialViewState.copy(email = email))
    }

    @Test
    fun `verify entered same password`() {
        vm.onPasswordChanged("")

        verifyViewState(initialViewState)
    }

    @Test
    fun `verify entered new password`() {
        val password = Random.nextString(20)
        vm.onPasswordChanged(password)

        verifyViewState(initialViewState.copy(password = password))
    }

    @Test
    fun `verify login with wrong email`() = runBlockingTest {
        val loginError = LoginError.InvalidEmail
        `when`(useCase.login(email, password)).then { LoginResult(null, loginError) }

        performLogin()

        assertEquals(initialViewState.copy(email = email, password = password, invalidEmail = true, isLoading = false), vm.viewState.value)
        assertNull(vm.userInfo.value)
        assertNull(vm.error.value)
        verify(useCase).login(email, password)
    }

    @Test
    fun `verify login with wrong password`() = runBlockingTest {
        val loginError = LoginError.InvalidPassword
        `when`(useCase.login(email, password)).then { LoginResult(null, loginError) }

        performLogin()

        assertEquals(initialViewState.copy(email = email, password = password, invalidPassword = true, isLoading = false), vm.viewState.value)
        assertNull(vm.userInfo.value)
        assertNull(vm.error.value)
        verify(useCase).login(email, password)
    }

    @Test
    fun `verify network error during login`() = runBlockingTest {
        val errorMessage = Random.nextString()
        verifyLoginError(LoginError.NetworkError(errorMessage), errorMessage)
    }

    @Test
    fun `verify server error during login`() = runBlockingTest {
        val errorMessage = Random.nextString()
        verifyLoginError(LoginError.ServerError(errorMessage), errorMessage)
    }

    @Test
    fun `verify other error during login`() = runBlockingTest {
        val errorMessage = Random.nextString()
        verifyLoginError(LoginError.Other(errorMessage), errorMessage)
    }

    @Test
    fun `verify success login`() = runBlockingTest {
        val user = User(Random.nextString(), Random.nextString(), Random.nextString(), Random.nextString())
        `when`(useCase.login(email, password)).then { LoginResult(user, null) }

        performLogin()

        assertEquals(initialViewState.copy(email = email, password = password), vm.viewState.value)
        assertNull(vm.error.value)
        assertEquals(user, vm.userInfo.value?.peekContent())
        verify(useCase).login(email, password)
    }

    private fun verifyViewState(viewState: LoginFragmentVM.LoginViewState) {
        assertEquals(viewState, vm.viewState.value)
        assertNull(vm.userInfo.value)
        assertNull(vm.error.value)
        verifyNoInteractions(useCase)
    }

    private fun performLogin() {
        vm.onEmailChanged(email)
        vm.onPasswordChanged(password)
        vm.performLogin()
    }

    private fun verifyLoginError(loginError: LoginError, message: String) = runBlockingTest {
        `when`(useCase.login(email, password)).then { LoginResult(null, loginError) }

        performLogin()

        assertEquals(initialViewState.copy(email = email, password = password, invalidPassword = false, isLoading = false), vm.viewState.value)
        assertNull(vm.userInfo.value)
        assertEquals(message, vm.error.value?.peekContent())
        verify(useCase).login(email, password)
    }

    companion object {
        private val initialViewState = LoginFragmentVM.LoginViewState(
            "", "",
            invalidEmail = false,
            invalidPassword = false,
            isLoading = false
        )

        val email = Random.nextString(10)
        val password = Random.nextString(20)
    }
}
