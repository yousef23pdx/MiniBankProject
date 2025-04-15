package com.example.Mini_Bank_Project.Accounts

import com.example.Mini_Bank_Project.DTO.*
import com.example.Mini_Bank_Project.TransferFundsException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal


@RestController
@RequestMapping("/accounts/v1")

class AccountsController(
    private val accountsService: AccountsService
) {

    @PostMapping("/accounts")
    fun createAccount(@RequestBody request: AccountRequest): ResponseEntity<Any> {
        return try {
            val response = accountsService.createAccount(request)
            ResponseEntity.ok(response)
        } catch (e: TransferFundsException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @GetMapping("/accounts")
    fun listAccounts(): ResponseEntity<Any> {
        return try {
            val accounts = accountsService.getAllAccounts()
            ResponseEntity.ok(accounts)
        } catch (e: TransferFundsException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))        }
    }

    @PostMapping("/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): ResponseEntity<Any> {
        return try {
            accountsService.closeAccount(accountNumber)
            ResponseEntity.ok().body("Account closed successfully")
        } catch (e: TransferFundsException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))        }
    }

    @PostMapping("/transfer")
    fun transferFunds(@RequestBody request: TransactionRequest): Any {
        return try {
            TransferFundsResponse(
                newBalance = accountsService.transferFunds(request)
            )
        } catch (e: TransferFundsException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))        }
    }


}

data class TransferFundsResponse(
    val newBalance: BigDecimal
)