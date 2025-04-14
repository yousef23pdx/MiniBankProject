package com.example.Mini_Bank_Project.Accounts

import com.example.Mini_Bank_Project.DTO.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/accounts/v1")

class AccountsController(
    private val accountsService: AccountsService
) {

    @PostMapping("/accounts")
    fun createAccount(@RequestBody request: AccountRequest): ResponseEntity<AccountResponse> {
        val response = accountsService.createAccount(request)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/accounts")
    fun listAccounts(): ResponseEntity<List<AccountResponse>> {
        val accounts = accountsService.getAllAccounts()
        return ResponseEntity.ok(accounts)
    }

    @PostMapping("/{accountNumber}/close")
    fun closeAccount(@PathVariable accountNumber: String): ResponseEntity<Void> {
        accountsService.closeAccount(accountNumber)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/transfer")
    fun transferFunds(@RequestBody request: TransactionRequest): ResponseEntity<String> {
        val result = accountsService.transferFunds(request)
        return ResponseEntity.ok(result)
    }


}