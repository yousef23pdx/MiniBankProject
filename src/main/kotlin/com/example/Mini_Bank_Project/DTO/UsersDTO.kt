package com.example.Mini_Bank_Project.DTO

data class UserRequest(

    val username: String,
    val passkey: String

)

data class UserResponse(
    val id: Long,
    val username: String
)

data class LoginRequest(
    val username: String,
    val passkey: String
)

data class LoginResponse(
    val message: String,
    val userId: Long
)