package com.hadiyarajesh.socialmedia.users

import com.hadiyarajesh.socialmedia.exception.ResourceNotFound
import com.hadiyarajesh.socialmedia.comments.CommentService
import com.hadiyarajesh.socialmedia.posts.PostService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val postService: PostService,
    private val commentService: CommentService
) {
    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun getUser(userId: Long): User {
        return userRepository.getUser(userId)
            ?: throw ResourceNotFound("User $userId not found")
    }

    fun editUser(userId: Long, username: String, fullName: String?, isPrivate: Boolean): User {
        return userRepository.editUser(userId, username, fullName, isPrivate)
            ?: throw ResourceNotFound("User $userId not found")
    }

    fun deleteUser(userId: Long) {
        commentService.deleteAllCommentsByUser(userId)
        postService.deleteAllPostsByUser(userId)
        userRepository.deleteUser(userId)
    }

    fun getAllUsers(page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return userRepository.getAllUsers(pageable)
    }
}