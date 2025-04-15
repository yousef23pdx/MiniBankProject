package com.example.Mini_Bank_Project.Users

import com.example.Mini_Bank_Project.DTO.UserRequest
import com.example.Mini_Bank_Project.DTO.UserResponse
import com.example.Mini_Bank_Project.TransferFundsException
import jakarta.inject.Named
import org.springframework.stereotype.Service

const val USERNAME_MIN_LENGTH = 4
const val USERNAME_MAX_LENGTH = 30
const val PASSKEY_MIN_LENGTH = 9
const val PASSKEY_MAX_LENGTH = 30

@Named
@Service
class UsersService(
    private val usersRepository: UsersRepository,
) {

  fun registerUser(request: UserRequest): UserResponse {

    if (request.username.length < USERNAME_MIN_LENGTH ||
        request.username.length > USERNAME_MAX_LENGTH) {
      throw TransferFundsException(
          "Username must be between ${USERNAME_MIN_LENGTH} and ${USERNAME_MAX_LENGTH} characters")
    }

    if (request.passkey.length < PASSKEY_MIN_LENGTH ||
        request.passkey.length > PASSKEY_MAX_LENGTH) {
      throw TransferFundsException(
          "Password must be between ${PASSKEY_MIN_LENGTH} and ${PASSKEY_MAX_LENGTH} characters")
    }

    val createUser = UserEntity(username = request.username, passkey = request.passkey)
    val savedUser = usersRepository.save(createUser)

    return UserResponse(id = savedUser.id!!, username = savedUser.username)
  }
}
