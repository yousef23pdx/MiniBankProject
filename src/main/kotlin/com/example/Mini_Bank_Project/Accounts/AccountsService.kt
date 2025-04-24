package com.example.Mini_Bank_Project.Accounts

import com.example.Mini_Bank_Project.DTO.*
import com.example.Mini_Bank_Project.Transactions.*
import com.example.Mini_Bank_Project.TransferFundsException
import com.example.Mini_Bank_Project.Users.UsersRepository
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*


@Service
class AccountsService(
    private val accountsRepository: AccountRepository,
    private val usersRepository: UsersRepository,
    private val transactionRepository: TransactionRepository,

){

    fun createAccount(request: AccountRequest): AccountResponse {

        // Find the user by ID
        val user = usersRepository.findById(request.userId)
            .orElseThrow { TransferFundsException("User not found") }

        // Check if user already has 5 accounts
        val existingAccounts = accountsRepository.findByUserId(user.id!!)
        if (existingAccounts.size >= 5) {
            throw TransferFundsException("User cannot have more than 5 accounts.")
        }

        // Generate a basic unique account number
        val accountNumber = UUID.randomUUID().toString()

        // Create the AccountEntity
        val account = AccountEntity(
            user = user,
            balance = request.initialBalance,
            isActive = true,
            accountNumber = accountNumber,
            name = request.name
        )

        // Save the account
        val saved = accountsRepository.save(account)

        // Return response DTO
        return AccountResponse(
            id = saved.id!!,
            userId = saved.user.id!!,
            balance = saved.balance,
            accountNumber = saved.accountNumber,
            isActive = saved.isActive,
            name = request.name
        )
    }

    fun getAllAccounts(): List<AccountResponse> {
        return accountsRepository.findAll().map { account ->
            AccountResponse(
                id = account.id!!,
                userId = account.user.id!!,
                balance = account.balance,
                accountNumber = account.accountNumber,
                isActive = account.isActive,
                name = account.name
            )
        }
    }

    fun closeAccount(accountNumber: String) {
        val account = accountsRepository.findAll()
            .firstOrNull { it.accountNumber == accountNumber }
            ?: throw TransferFundsException("Account not found")

        if (!account.isActive) throw TransferFundsException("Account already closed")

        account.isActive = false
        accountsRepository.save(account)
    }

    @Transactional
    fun transferFunds(request: TransactionRequest): BigDecimal {
        val source = accountsRepository.findByAccountNumber(request.sourceAccount)
        val destination = accountsRepository.findByAccountNumber(request.destinationAccount)

        if (source == null || destination == null) {
            throw TransferFundsException("Source or destination account not found")
        }

        if (!source.isActive || !destination.isActive) {
             throw TransferFundsException("One or both accounts are inactive")
        }

        if (source.balance < request.amount) {
            throw TransferFundsException("Insufficient balance in source account")
        }

        if (request.amount <= BigDecimal.ZERO) {
            throw TransferFundsException("Amount must be greater than zero.")
        }

        if (request.sourceAccount == request.destinationAccount) {
            throw TransferFundsException("You cannot transfer to the same account.")
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

        return source.balance
    }

}