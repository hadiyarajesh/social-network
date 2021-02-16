package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.Post
import com.hadiyarajesh.socialmedia.model.PostRequest
import com.hadiyarajesh.socialmedia.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService
) {
    @PostMapping("/{userId}")
    fun createPost(
        @PathVariable userId: Long,
        @RequestBody postRequest: PostRequest
    ): ResponseEntity<Map<String, Post>> {
        val createdPost = postService.createPost(userId, postRequest)
        val response = mapOf("post" to createdPost)
        return ResponseEntity.ok(response)
    }
}