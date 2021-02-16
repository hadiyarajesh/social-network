package com.example.neo4j.service

import com.example.neo4j.model.Post
import com.example.neo4j.model.PostRequest
import com.example.neo4j.repository.PostRepository
import org.springframework.stereotype.Service

@Service
//@Transactional
class PostService(
    private val userService: UserService,
    private val postRepository: PostRepository

) {
    fun createPost(userId: Long, postRequest: PostRequest): Post {
        val user = userService.getUserByUserId(userId)

        val post = Post(
            postId = postRequest.postId,
            mediaType = postRequest.mediaType,
            caption = postRequest.caption,
            user = user
        )
        return postRepository.save(post)
    }
}