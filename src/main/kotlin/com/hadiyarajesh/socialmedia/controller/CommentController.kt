package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.Comment
import com.hadiyarajesh.socialmedia.model.requests.CommentRequest
import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.service.CommentService
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
    fun getComment(@PathVariable commentId: Long): ResponseEntity<Map<String, Comment?>> {
        val comment = commentService.getComment(10000001, commentId)
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
    ): ResponseEntity<HashMap<String, Any?>> {
        val comments = commentService.getAllCommentsByPost(postId, page, size)
        val responseMap = hashMapOf<String, Any?>()
        responseMap["comments"] = comments.content
        responseMap["currentPage"] = comments.number
        responseMap["hasNext"] = comments.hasNext()
        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("/commenters")
    fun getAllPostCommenters(
        @PathVariable postId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val users = commentService.getPostCommenters(postId, page, size)
        return ResponseEntity.ok(createResponseMap(users))
    }

    @GetMapping("/total-commenters")
    fun getTotalPostCommenters(@PathVariable postId: Long): ResponseEntity<Map<String, Int>> {
        val totalCommenters = commentService.getTotalPostCommenters(postId)
        val responseMap = mapOf("totalCommenters" to totalCommenters)
        return ResponseEntity.ok(responseMap)
    }

    fun createResponseMap(userSlice: Slice<User>): HashMap<String, Any?> {
        val responseMap = hashMapOf<String, Any?>()
        responseMap["users"] = userSlice.content
        responseMap["currentPage"] = userSlice.number
        responseMap["hasNext"] = userSlice.hasNext()
        return responseMap
    }
}