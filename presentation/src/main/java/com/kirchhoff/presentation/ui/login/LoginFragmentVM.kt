package com.kirchhoff.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirchhoff.domain.models.LoginError
import com.kirchhoff.domain.models.User
import com.kirchhoff.domain.usecases.login.ILoginUseCase
import com.kirchhoff.presentation.utils.Event
import com.kirchhoff.presentation.utils.dispatchers.ICoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentVM @Inject constructor(
    private val useCase: ILoginUseCase,
    private val dispatchers: ICoroutineDispatchers
) : ViewModel() {

    private var loginJob: Job? = null

    private val _viewState = MutableLiveData<LoginViewState>()
    val viewState: LiveData<LoginViewState>
        get() = _viewState

    private val _userInfo = MutableLiveData<Event<User>>()
    val userInfo: LiveData<Event<User>>
        get() = _userInfo

    private val _error = MutableLiveData<Event<String>>()
    val error: LiveData<Event<String>>
        get() = _error

    private var currentViewState: LoginViewState = LoginViewState(
        "", "", invalidEmail = false, invalidPassword = false, isLoading = false
    )

    init {
        _viewState.postValue(currentViewState)
    }

    fun onEmailChanged(email: String) {
        if (email != currentViewState.email) {
            currentViewState = currentViewState.copy(email = email, invalidEmail = false)
            _viewState.postValue(currentViewState)
        }
    }

    fun onPasswordChanged(password: String) {
        if (password != currentViewState.password) {
            currentViewState = currentViewState.copy(password = password, invalidPassword = false)
            _viewState.postValue(currentViewState)
        }
    }

    fun performLogin() {
        currentViewState = currentViewState.copy(isLoading = true)
        _viewState.postValue(currentViewState)

        loginJob = viewModelScope.launch(dispatchers.IO) {
            val result = useCase.login(currentViewState.email, currentViewState.password)

            currentViewState = currentViewState.copy(isLoading = false)
            _viewState.postValue(currentViewState)

            if (result.user != null) {
                _userInfo.postValue(Event(result.user!!))
            } else if (result.error != null) {
                obtainError(result.error!!)
            }
        }
    }

    private fun obtainError(loginError: LoginError) {
        when (loginError) {
            LoginError.InvalidEmail -> currentViewState = currentViewState.copy(invalidEmail = true)
            LoginError.InvalidPassword -> currentViewState = currentViewState.copy(invalidPassword = true)
            is LoginError.NetworkError -> _error.postValue(Event(loginError.message))
            is LoginError.ServerError -> _error.postValue(Event(loginError.message))
            is LoginError.Other -> _error.postValue(Event(loginError.message))
        }

        if (loginError is LoginError.InvalidEmail || loginError is LoginError.InvalidPassword) {
            _viewState.postValue(currentViewState)
        }
    }

    fun cancelLogin() {
        loginJob?.cancel()
    }

    data class LoginViewState(
        val email: String,
        val password: String,
        val invalidEmail: Boolean,
        val invalidPassword: Boolean,
        val isLoading: Boolean
    )
}