package com.kirchhoff.domain.usecases.login

import com.kirchhoff.core.Result
import com.kirchhoff.domain.exceptions.IExceptionConverter
import com.kirchhoff.domain.models.LoginError
import com.kirchhoff.domain.models.LoginResult
import com.kirchhoff.domain.repository.ILoginRepository
import com.kirchhoff.domain.usecases.login.validator.ILoginValidator
import javax.inject.Inject

@Suppress("ReturnCount")
class LoginUseCase @Inject constructor(
    private val loginRepository: ILoginRepository,
    private val loginValidator: ILoginValidator,
    private val loginExceptionConverter: IExceptionConverter<LoginError>
) : ILoginUseCase {

    override suspend fun login(email: String, password: String): LoginResult {
        if (!loginValidator.isValidEmail(email)) {
            return LoginResult(error = LoginError.InvalidEmail)
        }

        if (!loginValidator.isValidPassword(password)) {
            return LoginResult(error = LoginError.InvalidPassword)
        }

        return when (val loginResult = loginRepository.login(email, password)) {
            is Result.Success -> LoginResult(user = loginResult.data)
            is Result.Exception -> LoginResult(error = loginExceptionConverter.convert(loginResult.exception))
        }
    }
}
