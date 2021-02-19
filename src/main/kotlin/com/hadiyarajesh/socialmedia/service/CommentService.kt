package com.hadiyarajesh.socialmedia.service

import com.hadiyarajesh.socialmedia.exception.ResourceNotFound
import com.hadiyarajesh.socialmedia.model.Comment
import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.repository.CommentRepository
import com.hadiyarajesh.socialmedia.repository.PostRepository
import com.hadiyarajesh.socialmedia.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    fun createComment(userId: Long, postId: Long, commentId: Long, text: String): Comment {
        val user = userRepository.findByUserId(userId)
            ?: throw ResourceNotFound("User $userId not found")

        return postRepository.findByPostId(postId).map { post ->
            commentRepository.save(
                Comment(
                    commentId = commentId,
                    text = text,
                    createdAt = Instant.now(),
                    post = post!!,
                    user = user
                )
            )
        }.orElseThrow { ResourceNotFound("Post $postId not found") }
    }

    fun deleteComment(userId: Long, postId: Long, commentId: Long): Boolean {
        commentRepository.deleteComment(userId, postId, commentId)
        return true
    }

    fun getPostCommenters(postId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return userRepository.getPostCommenters(postId, pageable)
    }

    fun getTotalPostCommenters(postId: Long): Int {
        return userRepository.getTotalPostCommenters(postId)
    }

    fun editComment(userId: Long, commentId: Long, text: String): Boolean {
        commentRepository.editCommentText(userId, commentId, text)
        return true
    }
}