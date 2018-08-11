package com.github.mrmeowcat.music_host.service


interface JwtService {

    fun createToken(username: String): String

    fun getUsername(token: String): String

}
