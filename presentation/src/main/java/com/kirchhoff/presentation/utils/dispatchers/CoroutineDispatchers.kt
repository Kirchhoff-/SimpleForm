package com.kirchhoff.presentation.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatchers : ICoroutineDispatchers {
    override val IO: CoroutineDispatcher
        get() = Dispatchers.IO
}
