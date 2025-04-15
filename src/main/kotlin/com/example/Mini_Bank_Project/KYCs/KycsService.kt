package com.example.Mini_Bank_Project.KYCs

import com.example.Mini_Bank_Project.DTO.*
import com.example.Mini_Bank_Project.Users.UsersRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import com.example.Mini_Bank_Project.KYCs.KycRepository
import com.example.Mini_Bank_Project.TransferFundsException
import jakarta.inject.Named

@Named
@Service
class KycService(
    private val kycRepository: KycRepository,
    private val usersRepository: UsersRepository
) {

    fun submitKyc(request: KycRequest): KycResponse {
        val user = usersRepository.findById(request.userId)
            .orElseThrow { TransferFundsException("User not found") }

        val existingKyc = kycRepository.findByUserId(request.userId)

        val kyc = if (existingKyc != null) {
            existingKyc.dateOfBirth = LocalDate.parse(request.dateOfBirth)
            existingKyc.nationality = request.nationality
            existingKyc.salary = request.salary
            existingKyc
        } else {
            KycEntity(
                user = user,
                dateOfBirth = LocalDate.parse(request.dateOfBirth),
                nationality = request.nationality,
                salary = request.salary
            )
        }

        val saved = kycRepository.save(kyc)

        return KycResponse(
            id = saved.id!!,
            userId = saved.user.id!!,
            dateOfBirth = saved.dateOfBirth,
            nationality = saved.nationality,
            salary = saved.salary
        )
    }

    fun getKycByUserId(userId: Long): KycResponse {
        val kyc = kycRepository.findByUserId(userId)
            ?: throw TransferFundsException("KYC not found for user $userId")

        return KycResponse(
            id = kyc.id!!,
            userId = kyc.user.id!!,
            dateOfBirth = kyc.dateOfBirth,
            nationality = kyc.nationality,
            salary = kyc.salary
        )
    }
}