package com.kirchhoff.presentation.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatchers : ICoroutineDispatchers {
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}
