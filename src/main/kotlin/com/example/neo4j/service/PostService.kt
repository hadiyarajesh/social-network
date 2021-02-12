package com.example.neo4j.service

import com.example.neo4j.model.Post
import com.example.neo4j.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository
) {
    fun createPost(post: Post) {
        postRepository.save(post)
    }
}