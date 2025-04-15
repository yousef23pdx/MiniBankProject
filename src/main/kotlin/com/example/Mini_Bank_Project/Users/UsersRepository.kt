package com.example.Mini_Bank_Project.Users

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository: JpaRepository<UserEntity, Long>


@Entity
@Table(name = "Users")

data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String,
    var passkey: String

) {
    constructor(): this(null,"","")
}