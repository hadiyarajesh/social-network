package com.example.neo4j.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
data class Comment(
    @Id
    @GeneratedValue
    var id: Long? = null,
    val commentId: Long,
    val text: String,

    @Relationship(type = "COMMENTED_ON", direction = Relationship.Direction.INCOMING)
    val post: Post
)
