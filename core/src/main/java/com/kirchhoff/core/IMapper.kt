package com.kirchhoff.core

interface IMapper<in FROM, out TO> {
    fun map(from: FROM): TO
}
