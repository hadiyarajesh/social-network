package com.hadiyarajesh.socialmedia.repository

import com.hadiyarajesh.socialmedia.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param

interface UserRepository : Neo4jRepository<User, Long> {
    fun findByUserId(userId: Long): User?

    @Query("MATCH (p: Post{postId:\$postId})-[h:HAS]->(c:Comment) WITH c MATCH (commenters)-[cc: CREATED_COMMENT]->(c) RETURN commenters ORDER BY commenters.id DESC SKIP \$skip LIMIT \$limit")
    fun getPostCommenters(
        @Param("postId") postId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (p: Post{postId:\$postId})-[h:HAS]->(c:Comment) WITH c MATCH (commenters)-[cc: CREATED_COMMENT]->(c) RETURN count(commenters)")
    fun getTotalPostCommenters(
        @Param("postId") postId: Long
    ): Int
}