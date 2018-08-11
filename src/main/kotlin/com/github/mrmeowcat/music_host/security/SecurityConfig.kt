package com.github.mrmeowcat.music_host.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
class SecurityConfig {

    @Value("\${music_host.username:admin}")
    lateinit var username: String

    @Value("\${music_host.password:password}")
    lateinit var password: String

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .securityContextRepository(securityContextRepository())
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/api/login").permitAll()
                .anyExchange().authenticated()
                .and()
                .build()
    }

    @Bean
    fun mapReactiveUserDetailsService(): MapReactiveUserDetailsService {
        val user: UserDetails = User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles()
                .build()
        return MapReactiveUserDetailsService(user)
    }

    @Bean
    fun authenticationManager() =
            UserDetailsRepositoryReactiveAuthenticationManager(mapReactiveUserDetailsService())

    @Bean
    fun securityContextRepository() = SecurityContextRepository()
}