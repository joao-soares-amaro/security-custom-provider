package com.amaro.ecp.securitycustomprovider.gateways.controllers

import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.beans.factory.annotation.Autowired
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat

@ExtendWith(MockitoExtension::class)
@WebMvcTest
internal class HelloWorldTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `hello should return Ok with message`() {
        val response = mockMvc.perform(get("/hello"))
            .andExpect {
                status().isOk
                content().string("Hi! I am on air.")
            }
    }
}