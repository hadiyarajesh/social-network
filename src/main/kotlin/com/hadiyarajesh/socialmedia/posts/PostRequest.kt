package com.hadiyarajesh.socialmedia.posts

data class PostRequest(
    val postId: Long,
    val mediaType: String?,
    val caption: String?,
)