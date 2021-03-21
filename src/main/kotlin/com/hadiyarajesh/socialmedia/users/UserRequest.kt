package com.hadiyarajesh.socialmedia.users

data class UserRequest(
    val username: String,
    val fullName: String?,
    val isPrivate: Boolean
)
