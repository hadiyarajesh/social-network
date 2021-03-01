package com.hadiyarajesh.socialmedia.repository

import com.hadiyarajesh.socialmedia.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param

interface UserRepository : Neo4jRepository<User, Long> {
    fun findByUserId(userId: Long): User?

    @Query("MATCH (u:User{userId:\$userId}) SET u.username = \$username, u.fullName = \$fullName, u.isPrivate = \$isPrivate return u")
    fun editUser(
        @Param("userId") userId: Long,
        @Param("username") username: String,
        @Param("fullName") fullName: String?,
        @Param("isPrivate") isPrivate: Boolean,
    ): User?

    @Query("MATCH (u:User{userId:\$userId}) DETACH DELETE u")
    fun deleteUser(userId: Long)

    @Query("MATCH (p:Post{postId:\$postId})-[h:HAS]->(c:Comment) WITH c MATCH (commenters)-[cc: CREATED_COMMENT]->(c) RETURN commenters ORDER BY commenters.id DESC SKIP \$skip LIMIT \$limit")
    fun getPostCommenters(
        @Param("postId") postId: Long,
        pageable: Pageable
    ): Slice<User>
}