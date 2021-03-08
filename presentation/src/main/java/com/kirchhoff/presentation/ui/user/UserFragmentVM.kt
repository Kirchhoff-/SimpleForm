package com.kirchhoff.presentation.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserFragmentVM @Inject constructor() : ViewModel() {

    private val _viewState = MutableLiveData<UserViewState>()
    val viewState: LiveData<UserViewState>
        get() = _viewState

    fun init(arg: UserFragmentArg) {
        _viewState.value = UserViewState(
            arg.name,
            arg.secondName,
            arg.info,
            arg.email
        )
    }

    data class UserViewState(
        val firstName: String,
        val lastName: String,
        val info: String,
        val email: String
    )
}
