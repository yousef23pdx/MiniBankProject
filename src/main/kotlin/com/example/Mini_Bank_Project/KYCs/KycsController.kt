package com.example.Mini_Bank_Project.KYCs

import com.example.Mini_Bank_Project.DTO.KycRequest
import com.example.Mini_Bank_Project.DTO.KycResponse
import com.example.Mini_Bank_Project.Users.UsersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController

class KycsController(

    private val kycService: KycService

){
    @PostMapping("/users/v1/kyc")
    fun submitKyc(@RequestBody request: KycRequest): ResponseEntity<KycResponse> {
        val kycSubmit = kycService.submitKyc(request)
        return ResponseEntity.ok(kycSubmit)
    }

    @GetMapping("/users/v1/kyc/{userId}")
    fun getKycByUserId(@PathVariable userId: Long): ResponseEntity<KycResponse> {
        val kycRetrieve = kycService.getKycByUserId(userId)
        return ResponseEntity.ok(kycRetrieve)
    }


}