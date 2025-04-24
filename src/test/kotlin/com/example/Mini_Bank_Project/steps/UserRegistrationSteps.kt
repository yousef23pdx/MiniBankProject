package com.example.Mini_Bank_Project.steps

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import kotlin.test.assertEquals

class UserRegistrationSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var response: ResponseEntity<String>
    lateinit var requestEntity: HttpEntity<String>

    @Given("I have a valid user registration payload")
    fun i_have_a_valid_user_registration_payload() {
        val jsonPayload = """
            {
              "username": "Test3",
              "passkey": "password123"
            }
        """.trimIndent()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        requestEntity = HttpEntity(jsonPayload, headers)
    }

    @When("I send a POST request to {string}")
    fun i_send_post_request(url: String) {
        response = restTemplate.postForEntity(url, requestEntity, String::class.java)
    }

    @Then("I should receive a {int} response")
    fun i_should_receive_200_ok(httpCode: Int) {
        assertEquals(httpCode, response.statusCode.value())
    }
}