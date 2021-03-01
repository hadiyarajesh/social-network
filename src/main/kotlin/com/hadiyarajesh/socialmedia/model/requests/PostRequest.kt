package com.hadiyarajesh.socialmedia.model.requests

data class PostRequest(
    val postId: Long,
    val mediaType: String?,
    val caption: String?,
)