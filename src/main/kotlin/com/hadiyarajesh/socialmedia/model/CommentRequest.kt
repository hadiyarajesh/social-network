package com.hadiyarajesh.socialmedia.model

data class CommentRequest(
    val commentId: Long,
    val postId: Long?,
    val text: String?
)
