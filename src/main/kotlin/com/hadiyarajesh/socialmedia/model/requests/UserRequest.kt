package com.hadiyarajesh.socialmedia.model.requests

data class UserRequest(
    val username: String,
    val fullName: String?,
    val isPrivate: Boolean
)
