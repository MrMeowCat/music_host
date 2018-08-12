package com.github.mrmeowcat.music_host.api

import io.jsonwebtoken.JwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.lang.IllegalArgumentException

/**
 * Handler for web exceptions.
 */
@Component
class ExceptionHandler : ErrorWebExceptionHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
    }

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        logger.error(ex.message, ex)
        when (ex) {
            is IllegalArgumentException,
            is JwtException -> exchange.response.statusCode = HttpStatus.BAD_REQUEST
            else -> exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        }
        return exchange.response.setComplete()
    }
}