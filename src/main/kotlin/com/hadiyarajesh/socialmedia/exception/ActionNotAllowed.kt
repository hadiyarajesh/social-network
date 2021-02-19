package com.hadiyarajesh.socialmedia.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
data class ActionNotAllowed(override val message: String): RuntimeException(message)
