package com.hadiyarajesh.socialmedia.likes

data class LikeRequest(
    val postId: Long,
    val commentId: Long? = null
)
