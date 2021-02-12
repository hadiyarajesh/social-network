package com.example.neo4j.service

import com.example.neo4j.model.Post
import com.example.neo4j.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
/*@Transactional
Getting
Transaction must be open, but has already been closed
if @Transactional is enabled
* */
class PostService(
    private val userService: UserService,
    private val postRepository: PostRepository

) {
    fun createPost(userId: Long, post: Post): Post {
        val user = userService.getUserByUserId(userId)
            ?: throw IllegalArgumentException("User $userId not found")

        val newPost = post.copy(
            postId = post.postId,
            mediaType = post.mediaType,
            caption = post.caption,
            user = user
        )
        return postRepository.save(newPost)
    }
}