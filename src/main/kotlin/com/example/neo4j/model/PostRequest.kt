package com.example.neo4j.model

data class PostRequest(
    val postId: Long,
    val mediaType: String,
    val caption: String?,
)
