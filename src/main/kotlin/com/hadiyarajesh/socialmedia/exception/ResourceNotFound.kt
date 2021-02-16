package com.hadiyarajesh.socialmedia.exception

data class ResourceNotFound(override val message: String): RuntimeException(message)
