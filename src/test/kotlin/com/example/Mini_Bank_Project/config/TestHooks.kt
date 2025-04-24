package com.example.Mini_Bank_Project.config

import com.example.Mini_Bank_Project.utils.GlobalToken
import io.cucumber.java.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

class TestHooks {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    companion object {
        private var isTokenGenerated = false
    }

    @Before
    fun generateJwtToken() {
        if (isTokenGenerated) return

        println("🚀 TestHooks running to generate token...")
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        // Register User
        val registrationPayload = """
            {
              "username": "GlobalUser",
              "passkey": "password123"
            }
        """.trimIndent()

        val registerRequest = HttpEntity(registrationPayload, headers)
        restTemplate.postForEntity("/users/v1/register", registerRequest, String::class.java)

        // Login to get Token
        val loginPayload = """
            {
              "username": "GlobalUser",
              "password": "password123"
            }
        """.trimIndent()

        val loginRequest = HttpEntity(loginPayload, headers)
        val response = restTemplate.postForEntity("/auth/login", loginRequest, String::class.java)

        val token = response.body?.substringAfter(":\"")?.substringBefore("\"}")
        GlobalToken.jwtToken = token

        println(" Global JWT Token Generated: $token")
        isTokenGenerated = true
    }
}