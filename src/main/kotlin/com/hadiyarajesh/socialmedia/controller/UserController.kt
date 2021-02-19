package com.hadiyarajesh.socialmedia.controller

import com.hadiyarajesh.socialmedia.model.User
import com.hadiyarajesh.socialmedia.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}