package com.example.neo4j.repository

import com.example.neo4j.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : Neo4jRepository<User, Long> {

    @Query("MATCH (u:User{userId:\$userId}) MATCH (p:Post{postId:\$postId}) MERGE (u)-[c:COMMENTED{commentId:\$commentId}]->(p) SET c.on = datetime(), c.text = \$text")
    fun createComment(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long,
        @Param("text") text: String,
    )

    @Query("MATCH (u:User{userId:\$userId})-[c:COMMENTED{commentId:\$commentId}]->(p:Post{postId:\$postId}) DELETE c")
    fun deleteComment(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    )

    @Query("MATCH (commenters)-[c:COMMENTED]->(p:Post{postId:\$postId}) RETURN commenters ORDER BY commenters.id DESC SKIP \$skip LIMIT \$limit")
    fun getPostCommenters(
        @Param("postId") postId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (commenters)-[c:COMMENTED]->(p:Post{postId:\$postId}) RETURN count(commenters)")
    fun getTotalPostCommenters(
        @Param("postId") postId: Long
    ): Int
}