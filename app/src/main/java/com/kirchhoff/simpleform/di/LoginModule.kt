package com.kirchhoff.simpleform.di

import com.kirchhoff.core.IMapper
import com.kirchhoff.data.models.NetworkUser
import com.kirchhoff.domain.exceptions.IExceptionConverter
import com.kirchhoff.domain.models.LoginError
import com.kirchhoff.domain.models.User
import com.kirchhoff.domain.repository.ILoginRepository
import com.kirchhoff.domain.usecases.login.ILoginUseCase
import com.kirchhoff.domain.usecases.login.LoginUseCase
import com.kirchhoff.domain.usecases.login.exceptions.LoginExceptionConverter
import com.kirchhoff.domain.usecases.login.validator.ILoginValidator
import com.kirchhoff.domain.usecases.login.validator.LoginValidator
import com.kirchhoff.mappers.NetworkUserToUserMapper
import com.kirchhoff.presentation.utils.dispatchers.CoroutineDispatchers
import com.kirchhoff.presentation.utils.dispatchers.ICoroutineDispatchers
import com.kirchhoff.repository.LoginRepository
import com.kirchhoff.service.IUserService
import com.kirchhoff.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    fun provideLoginValidator(): ILoginValidator = LoginValidator()

    @Provides
    fun provideNetworkUserToUserMapper(): IMapper<NetworkUser, User> = NetworkUserToUserMapper()

    @Provides
    fun provideLoginExceptionConverter(): IExceptionConverter<LoginError> = LoginExceptionConverter()

    @Singleton
    @Provides
    fun provideCoroutineDispatchers(): ICoroutineDispatchers = CoroutineDispatchers()

    @Singleton
    @Provides
    fun provideUserService(): IUserService = UserService()

    @Provides
    @Singleton
    fun provideLoginRepository(
        userService: IUserService,
        mapper: IMapper<@JvmSuppressWildcards NetworkUser, User>
    ): ILoginRepository = LoginRepository(userService, mapper)

    @Provides
    @Singleton
    fun provideLoginUseCase(
        loginRepository: ILoginRepository,
        loginValidator: ILoginValidator,
        loginExceptionConverter: IExceptionConverter<@JvmSuppressWildcards LoginError>
    ): ILoginUseCase = LoginUseCase(loginRepository, loginValidator, loginExceptionConverter)
}