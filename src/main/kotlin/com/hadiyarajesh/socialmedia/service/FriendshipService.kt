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
    fun followUser(currentUserId: Long, userToFollowId: Long): Boolean {
        if (currentUserId == userToFollowId) {
            throw IllegalArgumentException("You can not follow yourself")
        }
        if (friendshipRepository.isUserBlocked(currentUserId, userToFollowId)) {
            throw ActionNotAllowed("You are blocked by $userToFollowId")
        }

        return friendshipRepository.followUser(currentUserId, userToFollowId) ?: false
    }

    fun approveFollowRequest(currentUserId: Long, userToApproveFollowRequestId: Long): Boolean {
        return friendshipRepository.approveFollowRequest(currentUserId, userToApproveFollowRequestId) ?: false
    }

    fun rejectFollowRequest(currentUserId: Long, userToRejectFollowRequestId: Long): Boolean {
        return friendshipRepository.rejectFollowRequest(currentUserId, userToRejectFollowRequestId) ?: false
    }

    fun cancelFollowRequest(currentUserId: Long, userToCancelFollowRequestId: Long): Boolean {
        return friendshipRepository.cancelFollowRequest(currentUserId, userToCancelFollowRequestId) ?: false
    }

    fun unfollowUser(currentUserId: Long, userToUnfollowId: Long): Boolean {
        if (currentUserId == userToUnfollowId) {
            throw ActionNotAllowed("You can not unfollow yourself")
        }
        return friendshipRepository.unfollowUser(currentUserId, userToUnfollowId) ?: false
    }

    fun blockUser(currentUserId: Long, userToBlockId: Long): Boolean {
        return friendshipRepository.blockUser(currentUserId, userToBlockId) ?: false
    }

    fun unblockUser(currentUserId: Long, userToUnblockId: Long): Boolean {
        return friendshipRepository.unblockUser(currentUserId, userToUnblockId) ?: false
    }

    fun removeUser(currentUserId: Long, userToRemoveId: Long): Boolean {
        return friendshipRepository.removeUser(currentUserId, userToRemoveId) ?: false
    }

    fun getPendingFollowRequest(currentUserId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return friendshipRepository.getPendingFollowRequest(currentUserId, pageable)
    }

    fun getSentFollowRequest(currentUserId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return friendshipRepository.getSentFollowRequest(currentUserId, pageable)
    }

    fun getUserFollowing(currentUserId: Long, userId: Long, page: Int, size: Int): Slice<User> {
        isUserPrivateAndNotFollowedBy(currentUserId, userId)
        val pageable = PageRequest.of(page, size)
        return friendshipRepository.getUserFollowing(currentUserId, pageable)
    }

    fun getUserFollowers(currentUserId: Long, userId: Long, page: Int, size: Int): Slice<User> {
        isUserPrivateAndNotFollowedBy(currentUserId, userId)
        val pageable = PageRequest.of(page, size)
        return friendshipRepository.getUserFollowers(currentUserId, pageable)
    }

    fun isUserPrivateAndNotFollowedBy(currentUserId: Long, userId: Long): Boolean {
        val user = userRepository.findByUserId(userId)
            ?: throw ResourceNotFound("User $userId not found")
        if (user.isPrivate) {
            if (!isUserFollowing(currentUserId, userId)) {
                throw ActionNotAllowed("This is a private account")
            }
        }
        return true
    }

    fun isUserFollowing(currentUserId: Long, userToFollowId: Long): Boolean {
        return friendshipRepository.isUserFollowing(currentUserId, userToFollowId)
    }
}