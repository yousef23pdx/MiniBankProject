package com.example.Mini_Bank_Project.steps

import com.example.Mini_Bank_Project.utils.GlobalToken
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import kotlin.test.assertEquals

class UserAccountCreationSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    val responses = mutableListOf<ResponseEntity<String>>()

    val headers: HttpHeaders = HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON
        GlobalToken.jwtToken?.let { setBearerAuth(it) }  // Use global token
    }

    val globalUserId: Long = 1  // Assuming H2 assigns ID 1 for GlobalUser

    @When("I create {int} bank accounts for the global user")
    fun create_multiple_accounts(count: Int) {
        for (i in 1..count) {
            val accountPayload = """
                {
                  "userId": $globalUserId,
                  "name": "Account_$i",
                  "initialBalance": 1000.00
                }
            """.trimIndent()


            val request = HttpEntity(accountPayload, headers)
            println(" Using Token: ${GlobalToken.jwtToken}")
            val response = restTemplate.postForEntity("/accounts/v1/accounts", request, String::class.java)
            responses.add(response)
        }
    }

    @Then("I should receive {int} successful account creation responses")
    fun verify_account_creation(count: Int) {
        responses.forEach { response ->
            assertEquals(200, response.statusCode.value())
        }
        assertEquals(count, responses.size)
        println("Successfully created $count accounts using global token!")
    }
}