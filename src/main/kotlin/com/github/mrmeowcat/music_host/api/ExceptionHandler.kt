package com.github.mrmeowcat.music_host.api

import io.jsonwebtoken.JwtException
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.lang.IllegalArgumentException

@Component
class ExceptionHandler : ErrorWebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        when (ex) {
            is IllegalArgumentException,
            is JwtException -> exchange.response.statusCode = HttpStatus.BAD_REQUEST
            else -> exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        }
        return exchange.response.setComplete()
    }
}