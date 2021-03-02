package com.hadiyarajesh.socialmedia.service

import com.hadiyarajesh.socialmedia.exception.ResourceNotFound
import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val postService: PostService
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
        val countOfDeletedPosts = postService.deleteAllPostsByUser(userId)
        println("deleted posts : $countOfDeletedPosts")
        userRepository.deleteUser(userId)
    }

    fun getAllUsers(page: Int, size: Int): Slice<User> {
        val pageable = PageRequest.of(page, size)
        return userRepository.getAllUsers(pageable)
    }
}