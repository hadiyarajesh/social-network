package com.hadiyarajesh.socialmedia.service

import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.repository.FriendshipRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FriendshipService(
    private val friendshipRepository: FriendshipRepository
) {
    fun followUser(currentUserId: Long, userToFollowId: Long): Boolean {
        if (currentUserId == userToFollowId) {
            throw IllegalArgumentException("You can not follow yourself")
        }
        friendshipRepository.followUser(currentUserId, userToFollowId)
        return true
    }

    fun unfollowUser(currentUserId: Long, userToUnfollowId: Long): Boolean {
        if (currentUserId == userToUnfollowId) {
            throw IllegalArgumentException("You can not unfollow yourself")
        }
        friendshipRepository.unfollowUser(currentUserId, userToUnfollowId)
        return true
    }

    fun getUserFollowing(currentUserId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return friendshipRepository.getUserFollowing(currentUserId, pageable)
    }

    fun getUserFollowers(currentUserId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return friendshipRepository.getUserFollowers(currentUserId, pageable)
    }

    fun isUserFollowing(currentUserId: Long, userToFollowId: Long): Boolean {
        return friendshipRepository.isUserFollowing(currentUserId, userToFollowId)
    }
}