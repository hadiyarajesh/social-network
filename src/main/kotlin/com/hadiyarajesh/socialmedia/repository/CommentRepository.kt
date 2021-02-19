package com.hadiyarajesh.socialmedia.repository

import com.hadiyarajesh.socialmedia.model.Comment
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository: Neo4jRepository<Comment, Long> {

    @Query("RETURN EXISTS((:User{userId:\$userId})-[:CREATED_COMMENT]->(:Comment{commentId: 10000003}))")
    fun isCommentExistForUser(
        @Param("userId") userId: Long,
        @Param("commentId") commentId: Long
    )

    @Query("MATCH (u:User{userId:\$userId})-[cc:CREATED_COMMENT]->(c:Comment{commentId: \$commentId}) MATCH (p: Post{postId:\$postId})-[h:HAS]->(c:Comment{commentId:\$commentId}) DETACH DELETE c")
    fun deleteComment(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    )

    @Query("MATCH (u:User{userId:\$userId})-[cc:CREATED_COMMENT]->(c:Comment{commentId:\$commentId}) SET c.text = \$text")
    fun editCommentText(
        @Param("userId") userId: Long,
        @Param("commentId") commentId: Long,
        @Param("text") text: String,
    )
}