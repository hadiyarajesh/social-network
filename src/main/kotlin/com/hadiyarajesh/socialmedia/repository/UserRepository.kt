package com.hadiyarajesh.socialmedia.repository

import com.hadiyarajesh.socialmedia.model.User
import org.springframework.data.neo4j.repository.Neo4jRepository
import java.util.*

interface UserRepository : Neo4jRepository<User, Long> {
    fun findByUserId(userId: Long): User?
}