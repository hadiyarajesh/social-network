package com.example.neo4j.controller

import com.example.neo4j.model.FollowRequest
import com.example.neo4j.model.Post
import com.example.neo4j.model.User
import com.example.neo4j.repository.PostRepository
import com.example.neo4j.repository.UserRepository
import com.example.neo4j.service.PostService
import com.example.neo4j.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/neo4j")
class Neo4jController(
    private val userService: UserService,
    private val postService: PostService
) {

    @PostMapping("/users")
    fun createUser(@RequestBody user: User) {
        userService.createUser(user)
    }

    @PostMapping("/posts")
    fun createPost(@RequestBody post: Post) {
        postService.createPost(post)
    }

    @PostMapping("/follow/{userId}")
    fun follow(
        @PathVariable userId: String,
        @RequestBody followRequest: FollowRequest
    ) {

    }
}