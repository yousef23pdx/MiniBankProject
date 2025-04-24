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

class UserReadKycSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var response: ResponseEntity<String>

    val headers = HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON
        GlobalToken.jwtToken?.let { setBearerAuth(it) }
    }

    @Given("The global user has submitted KYC information")
    fun submit_kyc_if_needed() {
        val kycPayload = """
            {
              "userId": 1,
              "dateOfBirth": "2000-01-01",
              "nationality": "Kuwaiti",
              "salary": 2000.00
            }
        """.trimIndent()

        val request = HttpEntity(kycPayload, headers)
        val kycResponse = restTemplate.postForEntity("/users/v1/kyc", request, String::class.java)
        assertEquals(200, kycResponse.statusCode.value())
        println("KYC submitted for global user.")
    }

    @When("I request the KYC information for the global user")
    fun request_kyc_info() {
        val entity = HttpEntity<String>(headers)
        response = restTemplate.exchange("/users/v1/kyc/1", HttpMethod.GET, entity, String::class.java)
    }

    @Then("I should receive the correct KYC details")
    fun verify_kyc_details() {
        assertEquals(200, response.statusCode.value())
        assertTrue(response.body?.contains("Kuwaiti") == true, "KYC nationality should be 'Kuwaiti'")
        assertTrue(response.body?.contains("2000.0") == true, "KYC salary should be 2000.00")
        println("Retrieved KYC Info: ${response.body}")
    }
}