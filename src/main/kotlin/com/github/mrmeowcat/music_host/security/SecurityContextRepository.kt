package com.github.mrmeowcat.music_host.security

import com.github.mrmeowcat.music_host.service.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * Security context repository.
 */
class SecurityContextRepository : ServerSecurityContextRepository {

    private companion object {
        const val AUTHORIZATION_HEADER: String = "Authorization"
        const val BEARER_PREFIX: String = "Bearer "
    }

    @Autowired
    private lateinit var jwtService: JwtService

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> = Mono.empty()

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        val token: String? = exchange.request.headers[AUTHORIZATION_HEADER]
                ?.get(0)
                ?.substringAfter(BEARER_PREFIX)
        token ?: return Mono.just(SecurityContextImpl(null))
        val authentication: Authentication = jwtService.getAuthentication(token)
        return Mono.just(SecurityContextImpl(authentication))
    }
}
