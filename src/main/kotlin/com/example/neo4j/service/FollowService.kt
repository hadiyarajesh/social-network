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


        currentUser.friendships.add(FriendShip(Instant.now(), userToFollow))
        userRepository.save(currentUser)
        return true
    }
}