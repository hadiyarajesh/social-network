package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.LikeRequest
import com.hadiyarajesh.socialmedia.service.LikeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/likes")
class LikeController(
    private val likeService: LikeService
) {

    @PostMapping("like/{userId}")
    fun likePost(
        @PathVariable userId: Long,
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isLiked = likeService.likePost(userId, likeRequest.postId)
        val response = mapOf("isLiked" to isLiked)
        return ResponseEntity.ok(response)
    }

    @PostMapping("unlike/{userId}")
    fun unlikePost(
        @PathVariable userId: Long,
        @RequestBody likeRequest: LikeRequest
    ): ResponseEntity<Map<String, Boolean>> {
        val isUnliked = likeService.unlikePost(userId, likeRequest.postId)
        val response = mapOf("isUnliked" to isUnliked)
        return ResponseEntity.ok(response)
    }

    @GetMapping("likers/{postId}")
    fun getPostLikers(
        @PathVariable postId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val sliceable = likeService.getPostLikers(postId, page, size)

        val responseMap = hashMapOf<String, Any?>()
        responseMap["likers"] = sliceable.content
        responseMap["currentPage"] = sliceable.number
        responseMap["hasNext"] = sliceable.hasNext()

        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("totallikers/{postId}")
    fun getTotalPostLikers(@PathVariable postId: Long): ResponseEntity<Map<String, Int>> {
        val totalLikers = likeService.getTotalPostLikers(postId)
        val responseMap = mapOf("totalLikers" to totalLikers)
        return ResponseEntity.ok(responseMap)
    }
}