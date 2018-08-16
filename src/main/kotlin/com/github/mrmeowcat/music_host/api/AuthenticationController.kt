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

    data class SuccessResponse(val token: String, val authorities: List<String>)

    @PostMapping("login")
    fun login(@RequestBody credentials: Credentials): Mono<ResponseEntity<SuccessResponse>> {
        return authenticationManager
                .authenticate(UsernamePasswordAuthenticationToken(credentials.username, credentials.password))
                .map {
                    val authorities: List<String> = it.authorities.map { it.authority }
                    val token: String = jwtService.createToken(it.name, authorities)
                    val response = SuccessResponse(token, authorities)
                    ResponseEntity.ok(response) 
                }
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())
    }
}
