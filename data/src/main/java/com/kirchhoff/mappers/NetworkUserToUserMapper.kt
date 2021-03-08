package com.kirchhoff.mappers

import com.kirchhoff.core.IMapper
import com.kirchhoff.data.models.NetworkUser
import com.kirchhoff.domain.models.User

class NetworkUserToUserMapper: IMapper<NetworkUser, User> {

    override fun map(from: NetworkUser): User =
        User(
            from.firstName,
            from.lastName,
            from.info,
            from.email
        )
}