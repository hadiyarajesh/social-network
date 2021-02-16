package com.hadiyarajesh.socialmedia.model

data class PostRequest(
    val postId: Long,
    val mediaType: String,
    val caption: String?,
)
