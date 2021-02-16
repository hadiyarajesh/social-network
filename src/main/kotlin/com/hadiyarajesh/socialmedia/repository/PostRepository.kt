package com.hadiyarajesh.socialmedia.repository

import com.hadiyarajesh.socialmedia.model.Post
import org.springframework.data.neo4j.repository.Neo4jRepository

interface PostRepository: Neo4jRepository<Post, Long> {
}