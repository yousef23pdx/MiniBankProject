package com.example.Mini_Bank_Project.steps

import com.example.Mini_Bank_Project.utils.GlobalToken
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserCreateKycSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var response: ResponseEntity<String>

    val headers = HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON
        GlobalToken.jwtToken?.let { setBearerAuth(it) }   // Use global token
    }

    @Given("The global user is authenticated")
    fun global_user_authenticated() {
        println("Using Global Token for authenticated KYC submission.")
    }

    @When("I submit valid KYC information")
    fun submit_valid_kyc() {
        val kycPayload = """
            {
              "userId": 1,
              "dateOfBirth": "2000-01-01",
              "nationality": "Kuwaiti",
              "salary": 1500.00
            }
        """.trimIndent()

        val request = HttpEntity(kycPayload, headers)
        response = restTemplate.postForEntity("/users/v1/kyc", request, String::class.java)
    }

    @Then("I should receive a successful KYC response")
    fun verify_kyc_response() {
        assertEquals(200, response.statusCode.value())
        assertTrue(response.body?.contains("nationality") == true, "KYC response should contain submitted data")
        println("KYC Response: ${response.body}")
    }
}