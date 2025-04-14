package com.example.Mini_Bank_Project.KYCs

import com.example.Mini_Bank_Project.Users.UserEntity
import jakarta.inject.Named
import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal
import java.time.LocalDate

interface KycRepository : JpaRepository<KycEntity, Long> {
    fun findByUserId(userId: Long): KycEntity?
}



@Named
@Entity
@Table(name = "KYCs")
data class KycEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    val user: UserEntity,

    val dateOfBirth: LocalDate,
    val nationality: String,
    val salary: BigDecimal
) {
    constructor() : this(null, UserEntity(), LocalDate.now(), "", BigDecimal.ZERO)
}