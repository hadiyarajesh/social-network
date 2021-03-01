package com.hadiyarajesh.socialmedia.repository

import com.hadiyarajesh.socialmedia.model.User
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FriendshipRepository : Neo4jRepository<User, Long> {
    @Query("MATCH (u1:User{userId:\$currentUserId}) MATCH (u2:User{userId:\$userToFollowId}) WITH u1,u2, CASE WHEN u2.isPrivate THEN 'SENT_FOLLOW_REQUEST' ELSE 'FOLLOWING' END as relType CALL apoc.merge.relationship(u1, relType, {since: datetime()}, u2) YIELD rel RETURN true")
    fun followUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToFollowId") userToFollowId: Long
    ): Boolean?

    @Query("MATCH (u1:User{userId:\$currentUserId})<-[f1:SENT_FOLLOW_REQUEST]-(u2:User{userId:\$userToApproveFollowRequestId}) MERGE (u1)<-[f2:FOLLOWING]-(u2) SET f2.since = f1.since DELETE f1 RETURN true")
    fun approveFollowRequest(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToApproveFollowRequestId") userToApproveFollowRequestId: Long
    ): Boolean?

    @Query("MATCH (u1:User{userId:\$currentUserId})<-[f:SENT_FOLLOW_REQUEST]-(u2:User{userId:\$userToRejectFollowRequestId}) DELETE f RETURN true")
    fun rejectFollowRequest(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToRejectFollowRequestId") userToRejectFollowRequestId: Long
    ): Boolean?

    @Query("OPTIONAL MATCH (u1:User{userId:\$currentUserId})-[f:SENT_FOLLOW_REQUEST]->(u2:User{userId:\$userToCancelFollowRequestId}) DELETE f RETURN true")
    fun cancelFollowRequest(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToCancelFollowRequestId") userToCancelFollowRequestId: Long
    ): Boolean?

    @Query("MATCH (u1:User{userId:\$currentUserId})-[f:FOLLOWING]->(u2:User{userId:\$userToUnfollowId}) DELETE f RETURN true")
    fun unfollowUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToUnfollowId") userToUnfollowId: Long,
    ): Boolean?

    @Query("RETURN EXISTS((:User{userId:\$currentUserId})<-[:BLOCKED]-(:User{userId:\$userBlockedId}))")
    fun isUserBlocked(
        @Param("currentUserId") currentUserId: Long,
        @Param("userBlockedId") userBlockedId: Long
    ): Boolean

    @Query("MATCH (u1:User{userId:\$currentUserId}) MATCH (u2:User{userId:\$userToBlockId}) OPTIONAL MATCH (u1)<-[r]-(u2) DELETE r MERGE (u1)-[b:BLOCKED]->(u2) SET b.since = datetime() RETURN true")
    fun blockUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToBlockId") userToBlockId: Long
    ): Boolean?

    @Query("MATCH (u1:User{userId:\$currentUserId})-[b:BLOCKED]->(u2:User{userId:\$userToUnblockId}) DELETE b RETURN true")
    fun unblockUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToUnblockId") userToUnblockId: Long
    ): Boolean?

    @Query("MATCH (u1:User{userId:\$currentUserId})<-[f:FOLLOWING]-(u2:User{userId:\$userToRemoveId}) DELETE f RETURN true")
    fun removeUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToRemoveId") userToRemoveId: Long
    ): Boolean?

    @Query("RETURN EXISTS((:User{userId:\$currentUserId})-[:FOLLOWING]->(:User{userId:\$userToFollowId}))")
    fun isUserFollowing(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToFollowId") userToFollowId: Long
    ): Boolean

    @Query("MATCH (u:User{userId:\$currentUserId})-[f:FOLLOWING]->(users) RETURN users ORDER BY users.id DESC SKIP \$skip LIMIT \$limit")
    fun getUserFollowing(
        @Param("currentUserId") currentUserId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (u:User{userId:\$currentUserId})<-[f:FOLLOWING]-(users) RETURN users ORDER BY users.id DESC SKIP \$skip LIMIT \$limit")
    fun getUserFollowers(
        @Param("currentUserId") currentUserId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (u:User{userId:\$currentUserId})<-[f:SENT_FOLLOW_REQUEST]-(users) RETURN users ORDER BY users.id DESC SKIP \$skip LIMIT \$limit")
    fun getPendingFollowRequest(
        @Param("currentUserId") currentUserId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (u:User{userId:\$currentUserId})-[f:SENT_FOLLOW_REQUEST]->(users) RETURN users ORDER BY users.id DESC SKIP \$skip LIMIT \$limit")
    fun getSentFollowRequest(
        @Param("currentUserId") currentUserId: Long,
        pageable: Pageable
    ): Slice<User>
}