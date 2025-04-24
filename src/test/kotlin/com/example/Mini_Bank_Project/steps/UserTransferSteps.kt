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

class UserTransferSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var sourceAccountNumber: String
    lateinit var destinationAccountNumber: String
    lateinit var response: ResponseEntity<String>

    val headers = HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON
        GlobalToken.jwtToken?.let { setBearerAuth(it) }
    }

    @Given("A user has two active accounts with balances")
    fun create_two_accounts() {
        val accountPayload = """
            {
              "userId": 1,
              "name": "SourceAccount",
              "initialBalance": 500.00
            }
        """.trimIndent()

        val request = HttpEntity(accountPayload, headers)
        val sourceResponse = restTemplate.postForEntity("/accounts/v1/accounts", request, String::class.java)
        sourceAccountNumber = sourceResponse.body?.substringAfter("accountNumber\":\"")?.substringBefore("\"") ?: ""

        val destinationPayload = """
            {
              "userId": 1,
              "name": "DestinationAccount",
              "initialBalance": 200.00
            }
        """.trimIndent()

        val destRequest = HttpEntity(destinationPayload, headers)
        val destResponse = restTemplate.postForEntity("/accounts/v1/accounts", destRequest, String::class.java)
        destinationAccountNumber = destResponse.body?.substringAfter("accountNumber\":\"")?.substringBefore("\"") ?: ""

        println("Source: $sourceAccountNumber, Destination: $destinationAccountNumber")
    }

    @When("I transfer 100.00 from the first account to the second account")
    fun transfer_money() {
        val transferPayload = """
            {
              "sourceAccount": "$sourceAccountNumber",
              "destinationAccount": "$destinationAccountNumber",
              "amount": 100.00
            }
        """.trimIndent()

        val request = HttpEntity(transferPayload, headers)
        response = restTemplate.postForEntity("/accounts/v1/transfer", request, String::class.java)
    }

    @Then("I should receive confirmation of successful transfer with updated balance")
    fun verify_transfer() {
        assertEquals(200, response.statusCode.value())
        assertTrue(response.body?.contains("newBalance") == true)
        println("✅ Transfer successful! Response: ${response.body}")
    }
}