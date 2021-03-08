package com.kirchhoff.repository

import com.kirchhoff.core.IMapper
import com.kirchhoff.core.Result
import com.kirchhoff.data.models.NetworkUser
import com.kirchhoff.domain.exceptions.exceptions.LoginException
import com.kirchhoff.domain.models.User
import com.kirchhoff.domain.repository.ILoginRepository
import com.kirchhoff.service.IUserService
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class LoginRepository @Inject constructor(
    private val userService: IUserService,
    private val mapper: IMapper<NetworkUser, User>
) : ILoginRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        delay(Random.nextLong(FAKE_NETWORK_DELAY))

        return try {
            val user = userService.login(email, password)
            return Result.Success(mapper.map(user))
        } catch (e: Exception) {
            Result.Exception(
                LoginException(
                    e.message ?: STUB_ERROR_MESSAGE,
                    Random.nextInt(MAX_ERROR_NUMBER)
                )
            )
        }
    }

    companion object {
        private const val FAKE_NETWORK_DELAY = 10_000L
        private const val MAX_ERROR_NUMBER = 256
        private const val STUB_ERROR_MESSAGE = "Stub error message"
    }
}