package com.hadiyarajesh.socialmedia.users

import com.hadiyarajesh.socialmedia.utils.createResponseMapFromSlice
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
    ): ResponseEntity<Map<String, Any>> {
        val users = userService.getAllUsers(page, size)
        return ResponseEntity.ok(users.createResponseMapFromSlice("users"))
    }
}