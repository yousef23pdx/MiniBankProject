package com.example.Mini_Bank_Project.steps

import com.example.Mini_Bank_Project.utils.GlobalToken
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import kotlin.test.assertEquals


class UserCloseAccountSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var accountNumber: String
    lateinit var response: ResponseEntity<String>

    val headers = HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON
        GlobalToken.jwtToken?.let { setBearerAuth(it) }
    }

    @Given("The global user has an active bank account")
    fun create_active_account() {
        val accountPayload = """
            {
              "userId": 1,
              "name": "CloseMeAccount",
              "initialBalance": 1000.00
            }
        """.trimIndent()

        val request = HttpEntity(accountPayload, headers)
        val accountResponse = restTemplate.postForEntity("/accounts/v1/accounts", request, String::class.java)

        assertEquals(200, accountResponse.statusCode.value())

        accountNumber = accountResponse.body?.substringAfter("accountNumber\":\"")?.substringBefore("\"") ?: ""
        println("Created account with number: $accountNumber")
    }

    @When("I send a request to close that account")
    fun close_account() {
        println("Using Token: ${GlobalToken.jwtToken}")
        println("Attempting to close account: $accountNumber")

        val request = HttpEntity<String>(headers)
        response = restTemplate.exchange(
            "/accounts/v1/$accountNumber/close",
            HttpMethod.POST,
            request,
            String::class.java
        )
    }

    @Then("I should receive confirmation that the account is closed")
    fun verify_account_closure() {
        assertEquals(200, response.statusCode.value())
        println("Account $accountNumber successfully closed.")
    }
}