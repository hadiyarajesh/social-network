package com.example.neo4j.model

import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship

@Node
data class User(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val userId: Long,
    val username: String,
    val fullName: String?,

    @Relationship(type = "IS_FOLLOWING")
    val friendships: Set<FriendShip> = setOf(),
)
