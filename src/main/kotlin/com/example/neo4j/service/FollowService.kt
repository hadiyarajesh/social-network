package com.example.neo4j.service

import com.example.neo4j.model.FriendShip
import com.example.neo4j.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class FollowService(
    private val userRepository: UserRepository,
) {
    fun followUser(currentUserId: Long, userToFollowId: Long): Boolean {
        val userToFollow = userRepository.findByUserId(userToFollowId)
            ?: throw IllegalArgumentException("User $userToFollowId not found")

        val currentUser = userRepository.findByUserId(currentUserId)
            ?: throw IllegalArgumentException("User $currentUserId not found")

        val user = currentUser.copy(
            id = currentUser.id,
            userId = currentUser.userId,
            username = currentUser.username,
            fullName = currentUser.fullName,
            friendships = setOf(FriendShip(Instant.now(), userToFollow))
        )

        userRepository.save(user)
        return true
    }
}