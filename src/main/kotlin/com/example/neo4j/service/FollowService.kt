package com.example.neo4j.service

import com.example.neo4j.model.User
import com.example.neo4j.repository.FollowRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FollowService(
    private val followRepository: FollowRepository
) {
    fun followUser(currentUserId: Long, userToFollowId: Long): Boolean {
        if (currentUserId == userToFollowId) {
            throw IllegalArgumentException("You can not follow yourself")
        }

        followRepository.followUser(currentUserId, userToFollowId)
        return true
    }

    fun unfollowUser(currentUserId: Long, userToUnfollowId: Long): Boolean {
        if (currentUserId == userToUnfollowId) {
            throw IllegalArgumentException("You can not unfollow yourself")
        }

        followRepository.unfollowUser(currentUserId, userToUnfollowId)
        return true
    }

    fun getUserFollowing(currentUserId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return followRepository.getUserFollowing(currentUserId, pageable)
    }

    fun getUserFollowers(currentUserId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return followRepository.getUserFollowers(currentUserId, pageable)
    }

    fun isUserFollowing(currentUserId: Long, userToFollowId: Long): Boolean {
        return followRepository.isUserFollowing(currentUserId, userToFollowId)
    }
}