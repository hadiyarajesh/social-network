package com.hadiyarajesh.socialmedia.utils

import org.springframework.data.domain.Slice

fun <T> Slice<T>.createResponseMapFromSlice(label: String): Map<String, Any> {
    return mapOf(
        label to this.content,
        "currentPage" to this.number,
        "hasNextPage" to this.hasNext()
    )
}

object NodeRelationship {
    const val SENT_FOLLOW_REQUEST = "SENT_FOLLOW_REQUEST"
    const val FOLLOWING = "FOLLOWING"
    const val BLOCKED = "BLOCKED"

    const val CREATED_COMMENT = "CREATED_COMMENT"
    const val HAS_COMMENT = "HAS_COMMENT"

    const val LIKED_POST = "LIKED_POST"
    const val LIKED_COMMENT = "LIKED_COMMENT"
}