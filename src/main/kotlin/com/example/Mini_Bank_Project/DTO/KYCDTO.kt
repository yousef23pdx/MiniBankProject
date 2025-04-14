package com.example.Mini_Bank_Project.DTO

import java.math.BigDecimal
import java.time.LocalDate

data class KycRequest(
    val userId: Long,
    val dateOfBirth: String,       // format: "yyyy-MM-dd"
    val nationality: String,
    val salary: BigDecimal
)

data class KycResponse(
    val id: Long,
    val userId: Long,
    val dateOfBirth: LocalDate,
    val nationality: String,
    val salary: BigDecimal
)