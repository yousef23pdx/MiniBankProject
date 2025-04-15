package com.example.Mini_Bank_Project.DTO

import java.math.BigDecimal


data class AccountRequest(
    val userId: Long,
    val initialBalance: BigDecimal,
    val name: String
)

data class AccountResponse(
    val id: Long,
    val userId: Long,
    val balance: BigDecimal,
    val isActive: Boolean,
    val accountNumber: String,
    val name: String
)