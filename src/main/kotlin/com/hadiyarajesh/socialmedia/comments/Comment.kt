package com.hadiyarajesh.socialmedia.comments

import com.hadiyarajesh.socialmedia.users.User
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.Instant

@Node
data class Comment(
    @Id
    @GeneratedValue
    var id: Long? = null,
    val commentId: Long,
    val text: String,
    val createdAt: Instant,
    val totalLikes: Long,

    @Relationship(type = "CREATED_COMMENT", direction = Relationship.Direction.INCOMING)
    val user: User
)