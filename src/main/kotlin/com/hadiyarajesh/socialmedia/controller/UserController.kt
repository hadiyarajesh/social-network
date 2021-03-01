package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.model.requests.UserRequest
import com.hadiyarajesh.socialmedia.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/")
    fun createUser(@RequestBody user: User): ResponseEntity<Map<String, User>> {
        val user = userService.createUser(user)
        val response = mapOf("user" to user)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/{userId}")
    fun editUser(
        @PathVariable userId: Long,
        @RequestBody userRequest: UserRequest
    ): ResponseEntity<Map<String, User>> {
        val user = userService.editUser(userId, userRequest.username, userRequest.fullName, userRequest.isPrivate)
        val response = mapOf("user" to user)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable userId: Long
    ): ResponseEntity<Void> {
        userService.deleteUser(userId)
        return ResponseEntity.noContent().build()
    }
}