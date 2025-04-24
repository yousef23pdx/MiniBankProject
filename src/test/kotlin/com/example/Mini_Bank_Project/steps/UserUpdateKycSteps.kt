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

class UserUpdateKycSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var response: ResponseEntity<String>

    val headers = HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON
        GlobalToken.jwtToken?.let { setBearerAuth(it) }
    }

    @Given("The global user has existing KYC information")
    fun submit_initial_kyc() {
        val initialKycPayload = """
            {
              "userId": 1,
              "dateOfBirth": "2000-01-01",
              "nationality": "Kuwaiti",
              "salary": 1500.00
            }
        """.trimIndent()

        val request = HttpEntity(initialKycPayload, headers)
        val initialResponse = restTemplate.postForEntity("/users/v1/kyc", request, String::class.java)
        assertEquals(200, initialResponse.statusCode.value())
        println("Initial KYC submitted.")
    }

    @When("I submit updated KYC information")
    fun submit_updated_kyc() {
        val updatedKycPayload = """
            {
              "userId": 1,
              "dateOfBirth": "2000-01-01",
              "nationality": "Saudi",
              "salary": 2500.00
            }
        """.trimIndent()

        val request = HttpEntity(updatedKycPayload, headers)
        response = restTemplate.postForEntity("/users/v1/kyc", request, String::class.java)
    }

    @Then("I should receive the updated KYC response")
    fun verify_updated_kyc() {
        assertEquals(200, response.statusCode.value())
        assertTrue(response.body?.contains("Saudi") == true, "KYC nationality should be updated to Saudi")
        assertTrue(response.body?.contains("2500.0") == true, "KYC salary should be updated to 2500.00")
        println("Updated KYC Response: ${response.body}")
    }
}