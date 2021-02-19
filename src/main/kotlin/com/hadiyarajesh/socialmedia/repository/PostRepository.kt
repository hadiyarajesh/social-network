package com.hadiyarajesh.socialmedia.repository

import com.hadiyarajesh.socialmedia.model.Post
import org.springframework.data.neo4j.repository.Neo4jRepository
import java.util.*

interface PostRepository: Neo4jRepository<Post, Long> {
    fun findByPostId(postId: Long): Optional<Post>
}