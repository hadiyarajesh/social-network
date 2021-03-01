package com.hadiyarajesh.socialmedia.model.requests

data class LikeRequest(
    val postId: Long,
    val commentId: Long? = null
)
