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
        val createdUser = userService.createUser(user)
        val responseMap = mapOf("user" to createdUser)
        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<Map<String, User>> {
        val user = userService.getUser(userId)
        val responseMap = mapOf("user" to user)
        return ResponseEntity.ok(responseMap)
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

    @GetMapping("/all")
    fun getAllUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<HashMap<String, Any?>> {
        val users = userService.getAllUsers(page, size)
        val responseMap = hashMapOf<String, Any?>()
        responseMap["users"] = users.content
        responseMap["currentPage"] = users.number
        responseMap["hasNext"] = users.hasNext()
        return ResponseEntity.ok(responseMap)
    }
}