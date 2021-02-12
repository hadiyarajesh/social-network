package com.example.neo4j.repository

import com.example.neo4j.model.Post
import org.springframework.data.neo4j.repository.Neo4jRepository

interface PostRepository: Neo4jRepository<Post, Long> {
}