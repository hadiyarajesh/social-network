package com.example.neo4j.exception

data class ResourceNotFound(override val message: String): RuntimeException(message)
