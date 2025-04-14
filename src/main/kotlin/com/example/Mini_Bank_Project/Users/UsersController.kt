package com.example.Mini_Bank_Project.Users

import com.example.Mini_Bank_Project.DTO.UserRequest
import com.example.Mini_Bank_Project.DTO.UserResponse
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/users")


class UsersController(
    private val usersService: UsersService
){

    @PostMapping("/v1/register")
    fun registerUser(@RequestBody request: UserRequest): ResponseEntity<UserResponse> {

        val newUser = usersService.registerUser(request)
        return ResponseEntity.ok(newUser)

    }


}