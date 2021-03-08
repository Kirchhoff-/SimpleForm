package com.kirchhoff.presentation.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface ICoroutineDispatchers {
    val IO: CoroutineDispatcher
}