package com.kirchhoff.presentation.utils

import com.kirchhoff.presentation.utils.dispatchers.ICoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestDispatchers: ICoroutineDispatchers {
    override val IO: CoroutineDispatcher
        get() = Dispatchers.Main
}