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
        val post = postService.createPost(userId, postRequest)
        val response = mapOf("post" to post)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("edit/{postId}")
    fun editPost(
        @PathVariable postId: Long
    ) {

    }

    @DeleteMapping("delete/{postId}")
    fun deletePost(
        @PathVariable postId: Long
    ) {

    }
}