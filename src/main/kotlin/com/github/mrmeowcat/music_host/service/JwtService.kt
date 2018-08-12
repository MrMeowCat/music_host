package com.github.mrmeowcat.music_host.service

import org.springframework.security.core.Authentication

/**
 * Service for operations with JWT tokens.
 */
interface JwtService {

    /**
     * Creates a token by given username and roles.
     */
    fun createToken(username: String, roles: List<String>): String

    /**
     * Gets Authentication object from token.
     */
    fun getAuthentication(token: String): Authentication

}
