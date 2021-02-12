package com.example.neo4j.service

import com.example.neo4j.model.User
import com.example.neo4j.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(user: User) {

    }
}