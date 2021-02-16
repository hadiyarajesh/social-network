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

    @Query("MATCH (u:User{userId:\$userId}) MATCH (p:Post{postId:\$postId}) MERGE (u)-[l:LIKED]->(p) SET l.on = datetime()")
    fun likePost(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
    )

    @Query("MATCH (u:User{userId:\$userId})-[l:LIKED]->(p:Post{postId:\$postId}) DELETE l")
    fun unlikePost(
        @Param("userId") userId: Long,
        @Param("postId") postId: Long,
    )

    @Query("MATCH (likers)-[l:LIKED]->(p:Post{postId:\$postId}) RETURN likers ORDER BY likers.id DESC SKIP \$skip LIMIT \$limit")
    fun getPostLikers(
        @Param("postId") postId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (likers)-[l:LIKED]->(p:Post{postId:\$postId}) RETURN count(likers)")
    fun getTotalPostLikers(
        @Param("postId") postId: Long,
    ): Int
}