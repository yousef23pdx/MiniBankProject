package com.example.Mini_Bank_Project.KYCs

import com.example.Mini_Bank_Project.DTO.KycRequest
import com.example.Mini_Bank_Project.DTO.KycResponse
import com.example.Mini_Bank_Project.TransferFundsException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class KycsController(
    private val kycService: KycService
) {

    @PostMapping("/users/v1/kyc")
    fun submitKyc(@RequestBody request: KycRequest): ResponseEntity<Any> {
        return try {
            val kycSubmit = kycService.submitKyc(request)
            ResponseEntity.ok(kycSubmit)
        } catch (e: TransferFundsException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }

    @GetMapping("/users/v1/kyc/{userId}")
    fun getKycByUserId(@PathVariable userId: Long): ResponseEntity<Any> {
        return try {
            val kycRetrieve = kycService.getKycByUserId(userId)
            ResponseEntity.ok(kycRetrieve)
        } catch (e: TransferFundsException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        }
    }
}