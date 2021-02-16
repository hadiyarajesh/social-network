package com.example.neo4j.service

import com.example.neo4j.model.User
import com.example.neo4j.repository.LikeRepository
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
        likeRepository.likePost(userId, postId)
        return true
    }

    fun unlikePost(userId: Long, postId: Long): Boolean {
        likeRepository.unlikePost(userId, postId)
        return true
    }

    fun getPostLikers(postId: Long, page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return likeRepository.getPostLikers(postId, pageable)
    }

    fun getTotalPostLikers(postId: Long): Int {
        return likeRepository.getTotalPostLikers(postId)
    }
}