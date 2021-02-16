package com.example.neo4j.controller

import com.example.neo4j.model.CommentRequest
import com.example.neo4j.service.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping("create/{userId}")
    fun createComment(
        @PathVariable userId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isCommented =
            commentService.createComment(userId, commentRequest.postId, commentRequest.commentId, commentRequest.text!!)
        val response = mapOf("isCommented" to isCommented)
        return ResponseEntity.ok(response)
    }

    @PostMapping("delete/{userId}")
    fun deleteComment(
        @PathVariable userId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isCommentDeleted = commentService.deleteComment(userId, commentRequest.postId, commentRequest.commentId)
        val response = mapOf("isCommentDeleted" to isCommentDeleted)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/commenters/{postId}")
    fun getPostCommenters(
        @PathVariable postId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val sliceable = commentService.getPostCommenters(postId, page, size)

        val responseMap = hashMapOf<String, Any?>()
        responseMap["commenters"] = sliceable.content
        responseMap["currentPage"] = sliceable.number
        responseMap["hasNext"] = sliceable.hasNext()

        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("totalcommenters/{postId}")
    fun getTotalPostCommenters(@PathVariable postId: Long): ResponseEntity<Map<String, Int>> {
        val totalCommenters = commentService.getTotalPostCommenters(postId)
        val responseMap = mapOf("totalCommenters" to totalCommenters)
        return ResponseEntity.ok(responseMap)
    }
}