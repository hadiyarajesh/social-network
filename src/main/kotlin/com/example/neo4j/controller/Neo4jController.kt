package com.example.neo4j.controller

import com.example.neo4j.model.FriendshipRequest
import com.example.neo4j.model.Post
import com.example.neo4j.model.PostRequest
import com.example.neo4j.model.User
import com.example.neo4j.service.FollowService
import com.example.neo4j.service.PostService
import com.example.neo4j.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/neo4j")
class Neo4jController(
    private val userService: UserService,
    private val postService: PostService,
    private val followService: FollowService
) {

    @PostMapping("/users")
    fun createUser(@RequestBody user: User): ResponseEntity<Map<String, User>> {
        val createdUser = userService.createUser(user)
        val response = mapOf("user" to createdUser)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/posts/{userId}")
    fun createPost(
        @RequestBody postRequest: PostRequest,
        @PathVariable userId: Long
    ): ResponseEntity<Map<String, Post>> {
        val createdPost = postService.createPost(userId, postRequest)
        val response = mapOf("post" to createdPost)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/follow/{currentUserId}")
    fun followUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isFollowed = followService.followUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isFollowed" to isFollowed)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/unfollow/{currentUserId}")
    fun unfollowUser(
        @PathVariable currentUserId: Long,
        @RequestBody friendshipRequest: FriendshipRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isFollowed = followService.unfollowUser(currentUserId, friendshipRequest.userId)
        val response = mapOf("isUnfollowed" to isFollowed)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/following/{currentUserId}")
    fun getUserFollowing(
        @PathVariable currentUserId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val sliceable = followService.getUserFollowing(currentUserId, page, size)
        val responseMap = hashMapOf<String, Any?>()

        responseMap["following"] = sliceable?.content
        responseMap["currentPage"] = sliceable?.number
        responseMap["hasNext"] = sliceable?.hasNext()

        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("/followers/{currentUserId}")
    fun getUserFollowers(
        @PathVariable currentUserId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val sliceable = followService.getUserFollowers(currentUserId, page, size)

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
        val result = followService.isUserFollowing(currentUserId, userId)
        val responseMap = mapOf("isFollowing" to result)
        return ResponseEntity.ok(responseMap)
    }
}