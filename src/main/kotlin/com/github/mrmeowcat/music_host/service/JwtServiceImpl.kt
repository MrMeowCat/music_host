package com.github.mrmeowcat.music_host.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtServiceImpl : JwtService {

    @Value("\${jwt.secret:youlittlehuckersucker}")
    private lateinit var secret: String;

    @Value("\${jwt.age:2592000}") // 30 days
    private var age: Long = 2592000

    override fun createToken(username: String): String {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date(System.currentTimeMillis() * 1000 + age))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact()
    }

    override fun getUsername(token: String): String =
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
}