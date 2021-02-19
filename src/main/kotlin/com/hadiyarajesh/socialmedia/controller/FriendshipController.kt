package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.FriendshipRequest
import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.service.FriendshipService
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/friendship")
class FriendshipController(
    private val friendshipService: FriendshipService
) {
    @PostMapping("/follow/{currentUserId}")
    fun followUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isFollowed = friendshipService.followUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isFollowed" to isFollowed)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/approve/{currentUserId}")
    fun approveFollowRequest(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isAccepted = friendshipService.approveFollowRequest(currentUserId, friendshipRequest.userId)
        val response = mapOf("isAccepted" to isAccepted)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/reject/{currentUserId}")
    fun rejectFollowRequest(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isRejected = friendshipService.rejectFollowRequest(currentUserId, friendshipRequest.userId)
        val response = mapOf("isRejected" to isRejected)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/unfollow/{currentUserId}")
    fun unfollowUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isFollowed = friendshipService.unfollowUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isUnfollowed" to isFollowed)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/block/{currentUserId}")
    fun blockUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isBlocked = friendshipService.blockUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isBlocked" to isBlocked)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/unblock/{currentUserId}")
    fun unblockUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isUnblocked = friendshipService.unblockUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isUnblocked" to isUnblocked)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/pending/{currentUserId}")
    fun getPendingFollowRequest(
        @PathVariable currentUserId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val sliceable = friendshipService.getPendingFollowRequest(currentUserId, page, size)
        return ResponseEntity.ok(createResponseMap(sliceable))
    }

    @GetMapping("/following/{currentUserId}")
    fun getUserFollowing(
        @PathVariable currentUserId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val sliceable = friendshipService.getUserFollowing(currentUserId, page, size)
        return ResponseEntity.ok(createResponseMap(sliceable))
    }

    @GetMapping("/followers/{currentUserId}")
    fun getUserFollowers(
        @PathVariable currentUserId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val sliceable = friendshipService.getUserFollowers(currentUserId, page, size)
        return ResponseEntity.ok(createResponseMap(sliceable))
    }

    @GetMapping("/checkfollowing/{currentUserId}")
    fun checkUserFollowing(
        @PathVariable currentUserId: Long,
        @RequestParam userId: Long
    ): ResponseEntity<Map<String, Boolean>> {
        val result = friendshipService.isUserFollowing(currentUserId, userId)
        val responseMap = mapOf("isFollowing" to result)
        return ResponseEntity.ok(responseMap)
    }

    fun createResponseMap(sliceable: Slice<User>): HashMap<String, Any?> {
        val responseMap = hashMapOf<String, Any?>()
        responseMap["users"] = sliceable.content
        responseMap["currentPage"] = sliceable.number
        responseMap["hasNext"] = sliceable.hasNext()

        return responseMap
    }
}