package com.hadiyarajesh.socialmedia.comments

data class CommentRequest(
    val userId: Long,
    val commentId: Long,
    val text: String?
)
