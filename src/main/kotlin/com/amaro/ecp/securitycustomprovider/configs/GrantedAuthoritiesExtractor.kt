package com.amaro.ecp.securitycustomprovider.configs

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.stereotype.Component

@Component
class GrantedAuthoritiesExtractor : JwtAuthenticationConverter() {
    override fun extractAuthorities(jwt: Jwt): Collection<GrantedAuthority> {
        val authorities = listOf(jwt.claims["cognito:groups"].toString())
        return authorities.map { SimpleGrantedAuthority(it) }
    }
}