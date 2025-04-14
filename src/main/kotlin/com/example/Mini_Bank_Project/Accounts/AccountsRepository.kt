package com.example.Mini_Bank_Project.Accounts

import jakarta.persistence.*
import com.example.Mini_Bank_Project.Users.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal


@Repository
interface AccountRepository : JpaRepository<AccountEntity, Long> {

    fun findByUserId(userId: Long): List<AccountEntity>
    fun findByAccountNumber(accountNumber: String): AccountEntity?
}


@Entity
@Table(name = "Accounts")
data class AccountEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var isActive: Boolean = true,

    @Column(nullable = false, unique = true)
    var accountNumber: String = ""
) {
    constructor() : this(null, UserEntity(), BigDecimal.ZERO, true, "")
}