package com.hadiyarajesh.socialmedia.service

import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.repository.LikeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LikeService(
    private val likeRepository: LikeRepository
) {
    fun likePost(userId: Long, postId: Long): Boolean {
        return likeRepository.likePost(userId, postId) ?: false
    }

    fun unlikePost(userId: Long, postId: Long): Boolean {
        return likeRepository.unlikePost(userId, postId) ?: false
    }

    fun getPostLikers(postId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return likeRepository.getAllPostLikers(postId, pageable)
    }

    fun getTotalPostLikers(postId: Long): Int {
        return likeRepository.getTotalLikersCountByPost(postId)
    }

    fun likeComment(userId: Long, postId: Long, commentId: Long): Boolean {
        return likeRepository.likeComment(userId, postId, commentId) ?: false
    }

    fun unlikeComment(userId: Long, commentId: Long): Boolean {
        return likeRepository.unlikeComment(userId, commentId) ?: false
    }

    fun getCommentLikers(postId: Long, commentId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return likeRepository.getAllCommentLikers(postId, commentId, pageable)
    }

    fun getTotalCommentLikers(postId: Long, commentId: Long): Int {
        return likeRepository.getTotalLikersCountByComment(postId, commentId)
    }
}