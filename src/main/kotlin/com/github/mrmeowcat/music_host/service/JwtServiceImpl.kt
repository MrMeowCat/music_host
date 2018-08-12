package com.github.mrmeowcat.music_host.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

/**
 * Implementation of JwtService.
 */
@Service
class JwtServiceImpl : JwtService {

    companion object {
        const val ROLES_KEY: String = "roles"
    }

    @Value("\${jwt.secret:youlittlehuckersucker}")
    private lateinit var secret: String;

    @Value("\${jwt.age:2592000}") // 30 days
    private var age: Long = 2592000

    override fun createToken(username: String, roles: List<String>): String {
        val map: MutableMap<String, Any> = HashMap()
        map[ROLES_KEY] = roles
        return Jwts.builder()
                .setClaims(map)
                .setSubject(username)
                .setExpiration(Date(System.currentTimeMillis() * 1000 + age))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAuthentication(token: String): Authentication {
        val body: Claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        val authorities: List<GrantedAuthority> = if (body[ROLES_KEY] != null) {
            val roles: List<String> = body[ROLES_KEY] as List<String>
            roles.map { SimpleGrantedAuthority(it) }
        } else listOf()
        return UsernamePasswordAuthenticationToken(body.subject, null, authorities)
    }
}
