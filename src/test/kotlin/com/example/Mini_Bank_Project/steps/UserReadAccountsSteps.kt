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

class UserReadAccountsSteps {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var response: ResponseEntity<String>

    val headers = HttpHeaders().apply {
        contentType = MediaType.APPLICATION_JSON
        GlobalToken.jwtToken?.let { setBearerAuth(it) }   // Use global token for authorization
    }

    @Given("The global user has at least 1 account")
    fun ensure_user_has_account() {
        val accountPayload = """
            {
              "userId": 1,
              "name": "PrimaryAccount",
              "initialBalance": 500.00
            }
        """.trimIndent()

        val request = HttpEntity(accountPayload, headers)
        val accountResponse = restTemplate.postForEntity("/accounts/v1/accounts", request, String::class.java)

        assertEquals(200, accountResponse.statusCode.value())
        println(" Account created for global user.")
    }

    @When("I request the list of accounts")
    fun request_list_of_accounts() {
        val entity = HttpEntity<String>(headers)
        response = restTemplate.exchange("/accounts/v1/accounts", HttpMethod.GET, entity, String::class.java)
    }

    @Then("I should receive a list containing accounts")
    fun verify_list_of_accounts() {
        assertEquals(200, response.statusCode.value())
        assertTrue(response.body?.contains("accountNumber") == true, "Response should contain accounts data")
        println("Accounts List Response: ${response.body}")
    }
}
