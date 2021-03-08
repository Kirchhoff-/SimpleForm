package com.kirchhoff.mapper

import com.kirchhoff.data.models.NetworkUser
import com.kirchhoff.mappers.NetworkUserToUserMapper
import com.kirchhoff.utils.nextString
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class NetworkUserToUserMapperTest {

    private lateinit var mapper: NetworkUserToUserMapper

    @Before
    fun setup() {
        mapper = NetworkUserToUserMapper()
    }

    @Test
    fun `verify mapping from network user to user`() {
        val firstName = Random.nextString()
        val lastName = Random.nextString()
        val info = Random.nextString()
        val email = Random.nextString()
        val networkUser = NetworkUser(firstName, lastName, info, email)

        val resultUser = mapper.map(networkUser)

        assertEquals(firstName, resultUser.firstName)
        assertEquals(lastName, resultUser.lastName)
        assertEquals(info, resultUser.info)
        assertEquals(email, resultUser.email)
    }
}