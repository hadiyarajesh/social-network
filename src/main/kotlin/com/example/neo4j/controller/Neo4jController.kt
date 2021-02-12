package com.example.neo4j.controller

import com.example.neo4j.model.FollowRequest
import com.example.neo4j.model.Post
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
        @RequestBody post: Post,
        @PathVariable userId: Long
    ): ResponseEntity<Map<String, Post>> {
        val createdPost = postService.createPost(userId, post)
        val response = mapOf("post" to createdPost)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/follow/{currentUserId}")
    fun follow(
        @PathVariable currentUserId: Long,
        @RequestBody followRequest: FollowRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isFollowed = followService.followUser(currentUserId, followRequest.userToFollowId)
        val response = mapOf("isFollowed" to isFollowed)
        return ResponseEntity.ok(response)
    }
}