package com.example.Mini_Bank_Project.Transactions

import com.example.Mini_Bank_Project.Accounts.AccountEntity
import jakarta.persistence.*
import java.math.BigDecimal

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<TransactionEntity, Long>

@Entity
@Table(name = "transactions")
data class TransactionEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "source_account", nullable = false)
    val sourceAccount: AccountEntity,

    @ManyToOne
    @JoinColumn(name = "destination_account", nullable = false)
    val destinationAccount: AccountEntity,

    @Column(nullable = false)
    val amount: BigDecimal
)