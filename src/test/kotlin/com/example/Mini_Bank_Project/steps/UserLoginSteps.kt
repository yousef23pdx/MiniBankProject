package com.example.Mini_Bank_Project.steps

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserLoginSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var response: ResponseEntity<String>
    lateinit var loginRequest: HttpEntity<String>

    @Given("A registered user with username {string} and password {string}")
    fun a_registered_user_exists(username: String, password: String) {
        val registrationPayload = """
            {
              "username": "$username",
              "passkey": "$password"
            }
        """.trimIndent()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val registerRequest = HttpEntity(registrationPayload, headers)

        val registerResponse = restTemplate.postForEntity("/users/v1/register", registerRequest, String::class.java)
        assertEquals(200, registerResponse.statusCode.value())  // Ensure user registered

        // Prepare login payload
        val loginPayload = """
            {
              "username": "$username",
              "password": "$password"
            }
        """.trimIndent()

        loginRequest = HttpEntity(loginPayload, headers)
    }

    @When("I send a POST request to {string} for login")
    fun i_send_login_request(url: String) {
        response = restTemplate.postForEntity(url, loginRequest, String::class.java)
    }

    @Then("I should receive a {int} response with a JWT token")
    fun i_should_receive_token(httpCode: Int) {
        assertEquals(httpCode, response.statusCode.value())
        assertTrue(response.body?.contains("token") == true, "JWT token should be present in response")
        println("JWT Token Response: ${response.body}")
    }
}