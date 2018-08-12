package com.github.mrmeowcat.music_host.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.server.SecurityWebFilterChain

/**
 * Reactive security config.
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

    @Value("\${music_host.admin.username:admin}")
    private lateinit var adminUsername: String

    @Value("\${music_host.admin.password:password}")
    private lateinit var adminPassword: String

    @Value("\${music_host.guest.username:guest}")
    private lateinit var guestUsername: String

    @Value("\${music_host.guest.password:password}")
    private lateinit var guestPassword: String

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
        val admin: UserDetails = User.withDefaultPasswordEncoder()
                .username(adminUsername)
                .password(adminPassword)
                .roles("ADMIN")
                .build()
        val guest: UserDetails = User.withDefaultPasswordEncoder()
                .username(guestUsername)
                .password(guestPassword)
                .roles("GUEST")
                .build()
        return MapReactiveUserDetailsService(admin, guest)
    }

    @Bean
    fun authenticationManager() =
            UserDetailsRepositoryReactiveAuthenticationManager(mapReactiveUserDetailsService())

    @Bean
    fun securityContextRepository() = SecurityContextRepository()
}