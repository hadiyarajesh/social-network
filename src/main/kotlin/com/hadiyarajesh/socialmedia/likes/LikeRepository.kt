package com.hadiyarajesh.socialmedia.likes

import com.hadiyarajesh.socialmedia.users.User
import com.hadiyarajesh.socialmedia.utils.NodeRelationship
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository : Neo4jRepository<User, Long> {
    @Query("MATCH (u:User{userId:\$userId}) MATCH (p:Post{postId:\$postId}) MERGE (u)-[l:${NodeRelationship.LIKED_POST}]->(p) ON CREATE SET p.totalLikes = p.totalLikes + 1, l.createdAt = datetime() RETURN true")
    fun likePost(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
    ): Boolean?

    @Query("MATCH (u:User{userId:\$userId})-[l:${NodeRelationship.LIKED_POST}]->(p:Post{postId:\$postId}) p.totalLikes = p.totalLikes - 1 DELETE l RETURN true")
    fun unlikePost(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
    ): Boolean?

    @Query("MATCH (u:User{userId:\$userId}) MATCH (p:Post{postId:\$postId})-[:${NodeRelationship.HAS_COMMENT}]->(c:Comment{commentId:\$commentId}) MERGE (u)-[l:${NodeRelationship.LIKED_COMMENT}]->(c) ON CREATE SET c.totalLikes = c.totalLikes + 1, l.createdAt = datetime() RETURN true")
    fun likeComment(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    ): Boolean?

    @Query("MATCH (u:User{userId:\$userId}) MATCH (p:Post{postId:\$postId})-[:${NodeRelationship.HAS_COMMENT}]->(c:Comment{commentId:\$commentId}) MATCH (u)-[l:${NodeRelationship.LIKED_COMMENT}]->(c) SET c.totalLikes = c.totalLikes - 1 DELETE l RETURN true")
    fun unlikeComment(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    ): Boolean?

    @Query("MATCH (users)-[l:${NodeRelationship.LIKED_POST}]->(p:Post{postId:\$postId}) RETURN users ORDER BY users.id DESC SKIP \$skip LIMIT \$limit")
    fun getAllPostLikers(
        @Param("postId") postId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (users)-[l:${NodeRelationship.LIKED_POST}]->(p:Post{postId:\$postId}) RETURN count(users)")
    fun getPostLikersCount(
        @Param("postId") postId: Long,
    ): Int

    @Query("MATCH (p:Post{postId:\$postId})-[:${NodeRelationship.HAS_COMMENT}]->(c:Comment{commentId:\$commentId}) MATCH (users)-[l:${NodeRelationship.LIKED_COMMENT}]->(c) RETURN users ORDER BY users.id DESC SKIP \$skip LIMIT \$limit")
    fun getAllCommentLikers(
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (p:Post{postId:\$postId})-[:${NodeRelationship.HAS_COMMENT}]->(c:Comment{commentId:\$commentId}) MATCH (users)-[l:${NodeRelationship.LIKED_COMMENT}]->(c) RETURN count(users)")
    fun getCommentLikersCount(
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    ): Int
}