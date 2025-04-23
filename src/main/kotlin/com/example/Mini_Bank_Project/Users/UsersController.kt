package com.example.Mini_Bank_Project.Users

import com.example.Mini_Bank_Project.DTO.UserRequest
import com.example.Mini_Bank_Project.DTO.UserResponse
import com.example.Mini_Bank_Project.TransferFundsException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

  @PostMapping("/v1/register")
  fun registerUser(@RequestBody request: UserRequest): ResponseEntity<Any> {
    return try {
      val newUser = usersService.registerUser(request)
      ResponseEntity.ok(newUser)
    } catch (e: TransferFundsException) {
      ResponseEntity.badRequest().body(mapOf("error" to e.message))        }
  }

  @GetMapping("/v1/list")
  fun users() = usersService.listUsers()

  }
