package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.Comment
import com.hadiyarajesh.socialmedia.model.requests.CommentRequest
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
    ): ResponseEntity<HashMap<String, Any>> {
        val comments = commentService.getAllCommentsByPost(postId, page, size)
        return ResponseEntity.ok(createResponseMap("comments", comments))
    }

    @GetMapping("/commenters")
    fun getAllPostCommenters(
        @PathVariable postId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any>> {
        val users = commentService.getPostCommenters(postId, page, size)
        return ResponseEntity.ok(createResponseMap("users", users))
    }

    @GetMapping("/total-commenters")
    fun getTotalCommentersCountByPost(@PathVariable postId: Long): ResponseEntity<Map<String, Int>> {
        val totalCommenters = commentService.getTotalCommentersCountByPost(postId)
        val responseMap = mapOf("totalCommenters" to totalCommenters)
        return ResponseEntity.ok(responseMap)
    }

    fun <T> createResponseMap(label: String, slice: Slice<T>): HashMap<String, Any> {
        val responseMap = hashMapOf<String, Any>()
        responseMap[label] = slice.content
        responseMap["currentPage"] = slice.number
        responseMap["hasNext"] = slice.hasNext()
        return responseMap
    }
}