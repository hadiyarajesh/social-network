package com.example.neo4j.repository

import com.example.neo4j.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository: Neo4jRepository<User, Long> {
    @Query("MATCH (u1:User{userId:\$currentUserId}) MATCH (u2:User{userId:\$userToFollowId}) MERGE (u1)-[f:IS_FOLLOWING]->(u2) SET f.since = datetime()")
    fun followUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToFollowId") userToFollowId: Long
    )

    @Query("MATCH (u1:User{userId:\$currentUserId})-[f:IS_FOLLOWING]->(u2:User{userId:\$userToUnfollowId}) DELETE f")
    fun unfollowUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToUnfollowId") userToUnfollowId: Long,
    )

    @Query("MATCH (u1:User{userId:\$currentUserId})-[f:IS_FOLLOWING]->(following) RETURN following ORDER BY following.id DESC SKIP \$skip LIMIT \$limit")
    fun getUserFollowing(
        @Param("currentUserId") currentUserId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (u1:User{userId:\$currentUserId})<-[f:IS_FOLLOWING]-(following) RETURN following ORDER BY following.id DESC SKIP \$skip LIMIT \$limit")
    fun getUserFollowers(
        @Param("currentUserId") currentUserId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("RETURN EXISTS((:User{userId:\$currentUserId})-[:IS_FOLLOWING]->(:User{userId:\$userToFollowId}))")
    fun isUserFollowing(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToFollowId") userToFollowId: Long
    ): Boolean
}