package com.example.neo4j.model

data class CommentRequest(
    val commentId: Long,
    val postId: Long,
    val text: String?
)
