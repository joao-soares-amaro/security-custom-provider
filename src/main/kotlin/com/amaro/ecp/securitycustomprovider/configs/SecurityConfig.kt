package com.amaro.ecp.securitycustomprovider.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import java.util.*


@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // To configure method-level security
@ConfigurationProperties(prefix = "authorization")
data class SecurityConfig(
    val secure: List<SecurePathProperties>,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .requestMatchers { getAntMatchers(it) }
            .authorizeHttpRequests { getAntMatchers(it) }
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(grantedAuthoritiesExtractor())
        return http.build()
    }

    private fun grantedAuthoritiesExtractor(): JwtAuthenticationConverter {
        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter { jwt ->
            val list: List<String> = jwt.claims.getOrDefault("cognito:groups", emptyList<String>()) as List<String>
            return@setJwtGrantedAuthoritiesConverter list
                .map { obj: Any -> obj.toString() }
                .map { role -> SimpleGrantedAuthority("ROLE_${role.uppercase(Locale.getDefault())}") }
        }
        return jwtAuthenticationConverter
    }

    private fun getAntMatchers(requestMatcherConfigurer: HttpSecurity.RequestMatcherConfigurer): HttpSecurity.RequestMatcherConfigurer {
        secure.forEach {
            if (it.methods.contains("*")) requestMatcherConfigurer.antMatchers(it.path)
            else it.methods.forEach { method -> requestMatcherConfigurer.antMatchers(HttpMethod.valueOf(method), it.path) }
        }
        return requestMatcherConfigurer
    }

    private fun getAntMatchers(authorizeHttpRequestsConfigurer: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry): AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry {
        secure.forEach {
            if (it.methods.contains("*")) authorizeHttpRequestsConfigurer.antMatchers(it.path).hasAnyRole(*it.roles.toTypedArray())
            else it.methods.forEach { method -> authorizeHttpRequestsConfigurer.antMatchers(HttpMethod.valueOf(method), it.path).hasAnyRole(*it.roles.toTypedArray()) }
        }
        return authorizeHttpRequestsConfigurer
    }
}
