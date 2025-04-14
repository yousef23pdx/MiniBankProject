package com.example.Mini_Bank_Project.DTO
import java.math.BigDecimal

data class TransactionRequest(
    val sourceAccount: String,
    val destinationAccount: String,
    val amount: BigDecimal
)