package com.hadiyarajesh.socialmedia.repository

import com.hadiyarajesh.socialmedia.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository : Neo4jRepository<User, Long> {

    @Query("MATCH (u:User{userId:\$userId}) MATCH (p:Post{postId:\$postId}) MERGE (u)-[l:LIKED_POST]->(p) ON CREATE SET p.totalLikes = p.totalLikes + 1, l.since = datetime() RETURN true")
    fun likePost(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
    ): Boolean?

    @Query("MATCH (u:User{userId:\$userId})-[l:LIKED]->(p:Post{postId:\$postId}) p.totalLikes = p.totalLikes - 1 DELETE l RETURN true")
    fun unlikePost(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
    ): Boolean?

    @Query("MATCH (u:User{userId:\$userId}) MATCH (p:Post{postId:\$postId})-[:HAS_COMMENT]->(c:Comment{commentId:\$commentId}) MERGE (u)-[l:LIKED_COMMENT]->(c) ON CREATE SET c.totalLikes = c.totalLikes+1, l.since = datetime() RETURN true")
    fun likeComment(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    ): Boolean?

    @Query("MATCH (u:User{userId:\$userId})-[l:LIKED_COMMENT]->(c:Comment{commentId:\$commentId}) SET c.totalLikes = c.totalLikes-1 DELETE l RETURN true")
    fun unlikeComment(
        @Param("userId") userId: Long,
        @Param("commentId") commentId: Long
    ): Boolean?

    @Query("MATCH (users)-[l:LIKED_POST]->(p:Post{postId:\$postId}) RETURN users ORDER BY users.id DESC SKIP \$skip LIMIT \$limit")
    fun getAllPostLikers(
        @Param("postId") postId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (users)-[l:LIKED_POST]->(p:Post{postId:\$postId}) RETURN count(users)")
    fun getTotalLikersCountByPost(
        @Param("postId") postId: Long,
    ): Int

    @Query("MATCH (p:Post{postId:\$postId})-[:HAS_COMMENT]->(c:Comment{commentId:\$commentId}) MATCH (users)-[l:LIKED_COMMENT]->(c) RETURN users ORDER BY users.id DESC SKIP \$skip LIMIT \$limit")
    fun getAllCommentLikers(
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (p:Post{postId:\$postId})-[:HAS_COMMENT]->(c:Comment{commentId:\$commentId}) MATCH (users)-[l:LIKED_COMMENT]->(c) RETURN count(users)")
    fun getTotalLikersCountByComment(
        @Param("postId") postId: Long,
        @Param("commentId") commentId: Long
    ): Int
}