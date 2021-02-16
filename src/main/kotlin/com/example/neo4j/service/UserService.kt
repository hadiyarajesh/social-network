package com.example.neo4j.service

import com.example.neo4j.exception.ResourceNotFound
import com.example.neo4j.model.User
import com.example.neo4j.repository.UserRepository
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
}