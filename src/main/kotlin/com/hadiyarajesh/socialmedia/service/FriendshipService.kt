package com.hadiyarajesh.socialmedia.service

import com.hadiyarajesh.socialmedia.exception.ActionNotAllowed
import com.hadiyarajesh.socialmedia.exception.ResourceNotFound
import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.repository.FriendshipRepository
import com.hadiyarajesh.socialmedia.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FriendshipService(
    private val friendshipRepository: FriendshipRepository,
    private val userRepository: UserRepository
) {
//    fun followUser(currentUserId: Long, userToFollowId: Long): Boolean {
//        if (currentUserId == userToFollowId) {
//            throw IllegalArgumentException("You can not follow yourself")
//        }
//        friendshipRepository.followUser(currentUserId, userToFollowId)
//        return true
//    }

    fun followUser(currentUserId: Long, userToFollowId: Long): Boolean {
        if (currentUserId == userToFollowId) {
            throw IllegalArgumentException("You can not follow yourself")
        }

        if(friendshipRepository.isUserBlocked(currentUserId, userToFollowId)) {
            throw ActionNotAllowed("You're blocked by $userToFollowId")
        }

        val user = userRepository.findByUserId(userToFollowId)
            ?: throw ResourceNotFound("User $userToFollowId not found")

        when (user.isPrivate) {
            true -> {
                friendshipRepository.sendFollowRequestToUser(currentUserId, userToFollowId)
            }
            false -> {
                friendshipRepository.followUser(currentUserId, userToFollowId)
            }
        }
        return true
    }

    fun approveFollowRequest(currentUserId: Long, userToFollowId: Long): Boolean {
        friendshipRepository.approveFollowRequest(currentUserId, userToFollowId)
        return true
    }

    fun rejectFollowRequest(currentUserId: Long, userToFollowId: Long): Boolean {
        friendshipRepository.rejectFollowRequest(currentUserId, userToFollowId)
        return true
    }

    fun unfollowUser(currentUserId: Long, userToUnfollowId: Long): Boolean {
        if (currentUserId == userToUnfollowId) {
            throw IllegalArgumentException("You can not unfollow yourself")
        }
        friendshipRepository.unfollowUser(currentUserId, userToUnfollowId)
        return true
    }

    fun blockUser(currentUserId: Long, userToFollowId: Long): Boolean {
        friendshipRepository.blockUser(currentUserId, userToFollowId)
        return true
    }

    fun unblockUser(currentUserId: Long, userToFollowId: Long): Boolean {
        friendshipRepository.unblockUser(currentUserId, userToFollowId)
        return true
    }

    fun getPendingFollowRequest(currentUserId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return friendshipRepository.getPendingFollowRequest(currentUserId, pageable)
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