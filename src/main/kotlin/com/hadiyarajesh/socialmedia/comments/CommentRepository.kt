package com.hadiyarajesh.socialmedia.comments

import com.hadiyarajesh.socialmedia.utils.NodeRelationship
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : Neo4jRepository<Comment, Long> {
    @Query("MATCH (p:Post{postId:\$postId}) MATCH (c:Comment{commentId:\$commentId}) MERGE (p)-[h:${NodeRelationship.HAS_COMMENT}]->(c) ON CREATE SET p.totalComments = p.totalComments + 1 RETURN true")
    fun attachCommentToPost(
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    ): Boolean

    @Query("MATCH (pst:Post{postId:\$postId})-[:${NodeRelationship.HAS_COMMENT}]->(c:Comment{commentId:\$commentId}) MATCH p=(u:User)-[cc:${NodeRelationship.CREATED_COMMENT}]->(c) RETURN p")
    fun getComment(
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    ): Comment?

    @Query("MATCH (u:User{userId:\$userId})-[cc:${NodeRelationship.CREATED_COMMENT}]->(c:Comment{commentId:\$commentId}) SET c.text = \$text WITH u,c MATCH p=(u)-[cc]->(c) RETURN p")
    fun editCommentText(
        @Param("userId") userId: Long,
        @Param("commentId") commentId: Long,
        @Param("text") text: String,
    ): Comment?

    @Query("MATCH (u:User{userId:\$userId})-[cc:${NodeRelationship.CREATED_COMMENT}]->(c:Comment{commentId: \$commentId}) MATCH (p: Post{postId:\$postId})-[h:${NodeRelationship.HAS_COMMENT}]->(c:Comment{commentId:\$commentId}) SET p.totalComments = p.totalComments - 1 DETACH DELETE c RETURN true")
    fun deleteComment(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    ): Boolean?

    @Query("MATCH (p:Post{postId:\$postId})-[h:${NodeRelationship.HAS_COMMENT}]->(comments)<-[cc:${NodeRelationship.CREATED_COMMENT}]-(users) WITH users, comments MATCH p=(users)-[cc]->(comments) RETURN p ORDER BY comments.id DESC SKIP \$skip LIMIT \$limit")
    fun getAllCommentsByPost(
        @Param("postId") postId: Long,
        pageable: Pageable
    ): Slice<Comment>

    @Query("MATCH (p:Post{postId:\$postId})-[h:${NodeRelationship.HAS_COMMENT}]->(c:Comment) WITH c MATCH (commenters)-[cc: ${NodeRelationship.CREATED_COMMENT}]->(c) RETURN count(commenters)")
    fun getTotalCommentersCountByPost(
        @Param("postId") postId: Long
    ): Int

    @Query("CALL apoc.periodic.iterate('MATCH (p:Post{postId:\$postId}) RETURN p', 'MATCH (p)-[:${NodeRelationship.HAS_COMMENT}]->(comments) DETACH DELETE comments', {batchSize:100, params: {postId: \$postId}}) yield batches, total return total")
    fun deleteAllCommentsByPost(
        @Param("postId") postId: Long
    )

    @Query("CALL apoc.periodic.iterate('MATCH (u:User{userId:\$userId}) RETURN u', 'MATCH (u)-[:${NodeRelationship.CREATED_COMMENT}]->(comments) DETACH DELETE comments', {batchSize:100, params: {userId: \$userId}}) yield batches, total return total")
    fun deleteAllCommentsByUser(
        @Param("userId") userId: Long
    )
}