package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.Post
import com.hadiyarajesh.socialmedia.model.requests.PostRequest
import com.hadiyarajesh.socialmedia.service.PostService
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users/{userId}/posts")
class PostController(
    private val postService: PostService
) {
    @PostMapping("/")
    fun createPost(
        @PathVariable userId: Long,
        @RequestBody postRequest: PostRequest
    ): ResponseEntity<Map<String, Post>> {
        val post = postService.createPost(userId, postRequest.postId, postRequest.mediaType!!, postRequest.caption)
        val response = mapOf("post" to post)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{postId}")
    fun getPost(
        @PathVariable userId: Long,
        @PathVariable postId: Long,
    ): ResponseEntity<Map<String, Post>> {
        val post = postService.getPost(userId, postId)
        val response = mapOf("post" to post)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{postId}")
    fun editPost(
        @PathVariable userId: Long,
        @PathVariable postId: Long,
        @RequestBody postRequest: PostRequest
    ): ResponseEntity<Map<String, Post>> {
        val post = postService.editPost(userId, postId, postRequest.caption)
        val response = mapOf("post" to post)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable userId: Long,
        @PathVariable postId: Long
    ): ResponseEntity<Void> {
        postService.deletePost(userId, postId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/all")
    fun getAllPostByUser(
        @PathVariable userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<HashMap<String, Any>> {
        val posts = postService.getAllPostsByUser(userId, page, size)
        return ResponseEntity.ok(createResponseMap("posts", posts))
    }

    @GetMapping("/count")
    fun getTotalPostCountByUser(@PathVariable userId: Long): ResponseEntity<Map<String, Int>> {
        val postCount = postService.getTotalPostCountByUser(userId)
        val response = mapOf("totalPosts" to postCount)
        return ResponseEntity.ok(response)
    }

    fun <T> createResponseMap(label: String, slice: Slice<T>): HashMap<String, Any> {
        val responseMap = hashMapOf<String, Any>()
        responseMap[label] = slice.content
        responseMap["currentPage"] = slice.number
        responseMap["hasNext"] = slice.hasNext()
        return responseMap
    }
}