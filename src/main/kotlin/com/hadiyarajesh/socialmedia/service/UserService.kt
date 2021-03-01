package com.hadiyarajesh.socialmedia.service

import com.hadiyarajesh.socialmedia.exception.ResourceNotFound
import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun getUserByUserId(userId: Long): User {
        return userRepository.findByUserId(userId)
            ?: throw ResourceNotFound("User $userId not found")
    }

    fun editUser(userId: Long, username: String, fullName: String?, isPrivate: Boolean): User {
        return userRepository.editUser(userId, username, fullName, isPrivate)
            ?: throw ResourceNotFound("User $userId not found")
    }

    fun deleteUser(userId: Long) {
        userRepository.deleteUser(userId)
    }
}