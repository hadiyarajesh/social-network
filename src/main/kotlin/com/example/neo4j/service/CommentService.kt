package com.example.neo4j.service

import com.example.neo4j.model.User
import com.example.neo4j.repository.CommentRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository
) {
    fun createComment(userId: Long, postId: Long, commentId: Long, text: String): Boolean {
        commentRepository.createComment(userId, postId, commentId, text)
        return true
    }

    fun deleteComment(userId: Long, postId: Long, commentId: Long): Boolean {
        commentRepository.deleteComment(userId, postId, commentId)
        return true
    }

    fun getPostCommenters(postId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return commentRepository.getPostCommenters(postId, pageable)
    }

    fun getTotalPostCommenters(postId: Long): Int {
        return commentRepository.getTotalPostCommenters(postId)
    }
}