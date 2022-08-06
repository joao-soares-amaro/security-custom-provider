package com.amaro.ecp.securitycustomprovider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@ComponentScan("com.amaro.ecp.securitycustomprovider")
class SecurityCustomProviderApplication

fun main(args: Array<String>) {
	runApplication<SecurityCustomProviderApplication>(*args)
}
