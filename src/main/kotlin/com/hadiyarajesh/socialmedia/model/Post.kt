package com.hadiyarajesh.socialmedia.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship

data class Post(
    @Id
    @GeneratedValue
    var id: Long? = null,
    val postId: Long,
    val mediaType: String,
    val caption: String?,

    @Relationship(type = "CREATED", direction = Relationship.Direction.INCOMING)
    val user: User
)
