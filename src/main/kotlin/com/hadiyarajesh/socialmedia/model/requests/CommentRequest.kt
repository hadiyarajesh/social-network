package com.hadiyarajesh.socialmedia.model.requests

data class CommentRequest(
    val userId: Long,
    val commentId: Long,
    val text: String?
)
