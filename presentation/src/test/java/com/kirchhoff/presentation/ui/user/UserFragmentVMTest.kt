package com.kirchhoff.presentation.ui.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kirchhoff.presentation.utils.nextString
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@RunWith(MockitoJUnitRunner::class)
class UserFragmentVMTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var vm: UserFragmentVM

    @Before
    fun setup() {
        vm = UserFragmentVM()
    }

    @Test
    fun `verify init`() {
        val firstName = Random.nextString()
        val secondName = Random.nextString()
        val info = Random.nextString()
        val email = Random.nextString()
        val arg = UserFragmentArg(firstName, secondName, info, email)
        val expectedViewState = UserFragmentVM.UserViewState(firstName, secondName, info, email)

        vm.init(arg)

        assertEquals(expectedViewState, vm.viewState.value)
    }
}
