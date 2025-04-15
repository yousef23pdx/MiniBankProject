package com.example.Mini_Bank_Project.Accounts

import com.example.Mini_Bank_Project.DTO.*
import com.example.Mini_Bank_Project.Transactions.*
import com.example.Mini_Bank_Project.Users.UsersRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*


@Service
class AccountsService(
    private val accountsRepository: AccountRepository,
    private val usersRepository: UsersRepository,
    private val transactionRepository: TransactionRepository
){

    fun createAccount(request: AccountRequest): AccountResponse {
        // Find the user by ID
        val user = usersRepository.findById(request.userId)
            .orElseThrow { RuntimeException("User not found") }

        // Generate a basic unique account number
        val accountNumber = UUID.randomUUID().toString()

        // Create the AccountEntity
        val account = AccountEntity(
            user = user,
            balance = request.initialBalance,
            isActive = true,
            accountNumber = accountNumber
        )

        // Save the account
        val saved = accountsRepository.save(account)

        // Return response DTO
        return AccountResponse(
            id = saved.id!!,
            userId = saved.user.id!!,
            balance = saved.balance,
            accountNumber = saved.accountNumber,
            isActive = saved.isActive
        )
    }

    fun getAllAccounts(): List<AccountResponse> {
        return accountsRepository.findAll().map { account ->
            AccountResponse(
                id = account.id!!,
                userId = account.user.id!!,
                balance = account.balance,
                accountNumber = account.accountNumber,
                isActive = account.isActive
            )
        }
    }

    fun closeAccount(accountNumber: String) {
        val account = accountsRepository.findAll()
            .firstOrNull { it.accountNumber == accountNumber }
            ?: throw RuntimeException("Account not found")

        if (!account.isActive) throw RuntimeException("Account already closed")

        account.isActive = false
        accountsRepository.save(account)
    }

    fun transferFunds(request: TransactionRequest): String {
        val source = accountsRepository.findByAccountNumber(request.sourceAccount)
        val destination = accountsRepository.findByAccountNumber(request.destinationAccount)

        if (source == null || destination == null) {
            return "Source or destination account not found"
        }

        if (!source.isActive || !destination.isActive) {
            return "One or both accounts are inactive"
        }

        if (source.balance < request.amount) {
            return "Insufficient balance in source account"
        }

        if (request.amount <= BigDecimal.ZERO) {
            return "Amount must be greater than zero."
        }

        // Perform the transfer
        source.balance -= request.amount
        destination.balance += request.amount

        // Save both accounts
        accountsRepository.save(source)
        accountsRepository.save(destination)

        // Record the transaction
        val transaction = TransactionEntity(
            sourceAccount = source,
            destinationAccount = destination,
            amount = request.amount
        )
        transactionRepository.save(transaction)

        return "Transfer successful. New source balance: ${source.balance}"
    }


}


