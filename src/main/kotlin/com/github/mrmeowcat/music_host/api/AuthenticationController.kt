package com.github.mrmeowcat.music_host.api

import com.github.mrmeowcat.music_host.service.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * Authentication entry point.
 */
@RestController
@RequestMapping("/api")
class AuthenticationController(private val authenticationManager: ReactiveAuthenticationManager,
                               private val jwtService: JwtService) {

    data class Credentials(val username: String, val password: String)

    @PostMapping("login")
    fun login(@RequestBody credentials: Credentials): Mono<ResponseEntity<String>> {
        return authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken(credentials.username, credentials.password))
                .map { ResponseEntity.ok(jwtService.createToken(it.name, it.authorities.map { it.authority })) }
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())
    }
}
