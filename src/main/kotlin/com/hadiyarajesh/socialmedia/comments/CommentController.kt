package com.hadiyarajesh.socialmedia.comments

import com.hadiyarajesh.socialmedia.utils.createResponseMapFromSlice
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping("/")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Map<String, Comment>> {
        val comment = commentService.createComment(
            commentRequest.userId,
            postId,
            commentRequest.commentId,
            commentRequest.text!!
        )
        val response = mapOf("comment" to comment)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{commentId}")
    fun getComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): ResponseEntity<Map<String, Comment?>> {
        val comment = commentService.getComment(postId, commentId)
        val response = mapOf("comment" to comment)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/")
    fun editComment(
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Map<String, Comment>> {
        val comment = commentService.editComment(commentRequest.userId, commentRequest.commentId, commentRequest.text!!)
        val responseMap = mapOf("comment" to comment)
        return ResponseEntity.ok(responseMap)
    }

    @DeleteMapping("/")
    fun deleteComment(
        @PathVariable postId: Long,
        @RequestBody commentRequest: CommentRequest
    ): ResponseEntity<Void> {
        commentService.deleteComment(commentRequest.userId, postId, commentRequest.commentId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/all")
    fun getAllCommentsByPost(
        @PathVariable postId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val comments = commentService.getAllCommentsByPost(postId, page, size)
        return ResponseEntity.ok(comments.createResponseMapFromSlice("comments"))
    }

    @GetMapping("/commenters")
    fun getAllPostCommenters(
        @PathVariable postId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val users = commentService.getPostCommenters(postId, page, size)
        return ResponseEntity.ok(users.createResponseMapFromSlice("users"))
    }

    @GetMapping("/total-commenters")
    fun getTotalCommentersCountByPost(@PathVariable postId: Long): ResponseEntity<Map<String, Int>> {
        val totalCommenters = commentService.getTotalCommentersCountByPost(postId)
        val responseMap = mapOf("totalCommenters" to totalCommenters)
        return ResponseEntity.ok(responseMap)
    }
}