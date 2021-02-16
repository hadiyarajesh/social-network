package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.FriendshipRequest
import com.hadiyarajesh.socialmedia.service.FriendshipService
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

    @PostMapping("/unfollow/{currentUserId}")
    fun unfollowUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isFollowed = friendshipService.unfollowUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isUnfollowed" to isFollowed)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/following/{currentUserId}")
    fun getUserFollowing(
        @PathVariable currentUserId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val sliceable = friendshipService.getUserFollowing(currentUserId, page, size)

        val responseMap = hashMapOf<String, Any?>()
        responseMap["following"] = sliceable.content
        responseMap["currentPage"] = sliceable.number
        responseMap["hasNext"] = sliceable.hasNext()

        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("/followers/{currentUserId}")
    fun getUserFollowers(
        @PathVariable currentUserId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val sliceable = friendshipService.getUserFollowers(currentUserId, page, size)

        val responseMap = hashMapOf<String, Any?>()
        responseMap["followers"] = sliceable.content
        responseMap["currentPage"] = sliceable.number
        responseMap["hasNext"] = sliceable.hasNext()

        return ResponseEntity.ok(responseMap)
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
}