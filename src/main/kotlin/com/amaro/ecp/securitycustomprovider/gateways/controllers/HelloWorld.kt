package com.amaro.ecp.securitycustomprovider.gateways.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus

@RestController
class HelloWorld {
    @GetMapping("hello")
    @ResponseStatus(HttpStatus.OK)
    fun hello(): String {
        return "Hi! I am on air."
    }

    @GetMapping("forbidden")
    @ResponseStatus(HttpStatus.OK)
    fun forbidden(): String {
        return "You should not be able to see this message"
    }
}