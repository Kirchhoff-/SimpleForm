package com.kirchhoff.presentation.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.kirchhoff.domain.models.User
import com.kirchhoff.presentation.R
import com.kirchhoff.presentation.databinding.FragmentLoginBinding
import com.kirchhoff.presentation.extensions.hideKeyboard
import com.kirchhoff.presentation.extensions.setTextIfRequired
import com.kirchhoff.presentation.extensions.showError
import com.kirchhoff.presentation.ui.base.BaseFragment
import com.kirchhoff.presentation.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val viewModel: LoginFragmentVM by viewModels()
    private val viewBinding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        subscribeViewModel()
    }

    private fun setupViews() {
        with(viewBinding) {
            etEmail.doAfterTextChanged { viewModel.onEmailChanged(it.toString()) }
            etPassword.doAfterTextChanged { viewModel.onPasswordChanged(it.toString()) }
            bLogin.setOnClickListener {
                view?.hideKeyboard()
                viewModel.performLogin()
            }
        }
    }

    private fun subscribeViewModel() {
        with(viewModel) {
            viewState.subscribe { renderViewState(it) }
            error.subscribeEvent { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            userInfo.subscribeEvent { moveToUserScreen(it) }
        }
    }

    private fun renderViewState(viewState: LoginFragmentVM.LoginViewState) {
        with(viewBinding) {
            etEmail.setTextIfRequired(viewState.email)
            etEmail.showError(viewState.invalidEmail, R.string.invalid_email)
            etPassword.setTextIfRequired(viewState.password)
            etPassword.showError(viewState.invalidPassword, R.string.invalid_password)
            bLogin.isEnabled = viewState.email.isNotEmpty() && viewState.password.isNotEmpty()
            showProgressDialog(viewState.isLoading) { viewModel.cancelLogin() }
        }
    }

    private fun moveToUserScreen(user: User) {
        //Add navigation after creating correspondent fragment
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissProgressDialog()
    }
}