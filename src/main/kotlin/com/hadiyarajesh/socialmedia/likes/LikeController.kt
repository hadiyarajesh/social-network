package com.hadiyarajesh.socialmedia.likes

import com.hadiyarajesh.socialmedia.utils.createResponseMapFromSlice
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/likes")
class LikeController(
    private val likeService: LikeService
) {
    @PostMapping("like/post/{userId}")
    fun likePost(
        @PathVariable userId: Long,
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isLiked = likeService.likePost(userId, likeRequest.postId)
        val response = mapOf("isLiked" to isLiked)
        return ResponseEntity.ok(response)
    }

    @PostMapping("unlike/post/{userId}")
    fun unlikePost(
        @PathVariable userId: Long,
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isUnliked = likeService.unlikePost(userId, likeRequest.postId)
        val response = mapOf("isUnliked" to isUnliked)
        return ResponseEntity.ok(response)
    }

    @GetMapping("likers/post/{postId}")
    fun getPostLikers(
        @PathVariable postId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val users = likeService.getPostLikers(postId, page, size)
        return ResponseEntity.ok(users.createResponseMapFromSlice("users"))
    }

    @GetMapping("totallikers/post/{postId}")
    fun getTotalPostLikers(@PathVariable postId: Long): ResponseEntity<Map<String, Int>> {
        val totalLikers = likeService.getTotalPostLikers(postId)
        val responseMap = mapOf("totalLikers" to totalLikers)
        return ResponseEntity.ok(responseMap)
    }

    @PostMapping("like/comment/{userId}")
    fun likeComment(
        @PathVariable userId: Long,
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isLiked = likeService.likeComment(userId, likeRequest.postId, likeRequest.commentId!!)
        val response = mapOf("isLiked" to isLiked)
        return ResponseEntity.ok(response)
    }

    @PostMapping("unlike/comment/{userId}")
    fun unlikeComment(
        @PathVariable userId: Long,
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isUnliked = likeService.unlikeComment(userId, likeRequest.postId, likeRequest.commentId!!)
        val response = mapOf("isUnliked" to isUnliked)
        return ResponseEntity.ok(response)
    }

    @GetMapping("likers/comment/{postId}")
    fun getCommentLikers(
        @PathVariable postId: Long,
        @RequestParam commentId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val users = likeService.getCommentLikers(postId, commentId, page, size)
        return ResponseEntity.ok(users.createResponseMapFromSlice("users"))
    }

    @GetMapping("totallikers/comment/{postId}")
    fun getTotalCommentLikers(
        @PathVariable postId: Long,
        @RequestParam commentId: Long,
    ): ResponseEntity<Map<String, Int>> {
        val totalLikers = likeService.getTotalCommentLikers(postId, commentId)
        val responseMap = mapOf("totalLikers" to totalLikers)
        return ResponseEntity.ok(responseMap)
    }
}