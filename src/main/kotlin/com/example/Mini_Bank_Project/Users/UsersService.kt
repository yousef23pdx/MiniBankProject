package com.example.Mini_Bank_Project.Users

import com.example.Mini_Bank_Project.Accounts.DTO.LoginRequest
import com.example.Mini_Bank_Project.Accounts.DTO.UserRequest
import com.example.Mini_Bank_Project.Accounts.DTO.UserResponse
import jakarta.inject.Named
import org.springframework.stereotype.Service

@Named
@Service
class UsersService(

    private val usersRepository: UsersRepository,


    ) {

    fun registerUser(request: UserRequest): UserResponse {

        val createUser = UserEntity( username = request.username, passkey = request.passkey)
        val savedUser =usersRepository.save(createUser)

        return UserResponse(id = savedUser.id !!, username = savedUser.username)

    }


}
