package com.hadiyarajesh.socialmedia.model

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

    @Relationship(type = "HAS", direction = Relationship.Direction.INCOMING)
    val post: Post,

    @Relationship(type = "CREATED_COMMENT", direction = Relationship.Direction.INCOMING)
    val user: User
)
