package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.User
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
        val createdUser = userService.createUser(user)
        val response = mapOf("user" to createdUser)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("edit/{userId}")
    fun editUser(
        @PathVariable userId: Long
    ) {

    }

    @DeleteMapping("delete/{userId}")
    fun deleteUser(
        @PathVariable userId: Long
    ) {

    }
}