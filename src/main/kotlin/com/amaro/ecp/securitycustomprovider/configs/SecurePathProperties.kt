package com.amaro.ecp.securitycustomprovider.configs

import javax.validation.constraints.NotBlank

class SecurePathProperties {
    @NotBlank
    lateinit var path: String
    var methods: List<String> = listOf("*")
    var roles: List<String> = listOf("ADMIN")
}
