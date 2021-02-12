package com.example.neo4j.model

import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode
import java.time.Instant

@RelationshipProperties()
data class FriendShip(
    val since: Instant,

    @TargetNode
    val targetNode: User
)
