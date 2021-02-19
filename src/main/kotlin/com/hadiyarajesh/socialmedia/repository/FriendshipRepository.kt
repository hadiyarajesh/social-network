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
    @Query("MATCH (u1:User{userId:\$currentUserId}) MATCH (u2:User{userId:\$userToFollowId}) MERGE (u1)-[f:FOLLOWING]->(u2) SET f.since = datetime()")
    fun followUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToFollowId") userToFollowId: Long
    )

    @Query("MATCH (u1:User{userId:\$currentUserId}) MATCH (u2:User{userId:\$userToFollowId}) MERGE (u1)-[f:SENT_FOLLOW_REQUEST]->(u2) SET f.since = datetime()")
    fun sendFollowRequestToUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToFollowId") userToFollowId: Long
    )

    @Query("MATCH (u1:User{userId:\$currentUserId})<-[f1:SENT_FOLLOW_REQUEST]-(u2:User{userId:\$userToApproveFollowRequestId}) MERGE (u1)<-[f2:FOLLOWING]-(u2) SET f2.since = f1.since DELETE f1")
    fun approveFollowRequest(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToApproveFollowRequestId") userToApproveFollowRequestId: Long
    )

    @Query("MATCH (u1:User{userId:\$currentUserId})<-[f:SENT_FOLLOW_REQUEST]-(u2:User{userId:\$userToRejectFollowRequestId}) DELETE f")
    fun rejectFollowRequest(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToRejectFollowRequestId") userToRejectFollowRequestId: Long
    )

    @Query("MATCH (u1:User{userId:\$currentUserId})-[f:FOLLOWING]->(u2:User{userId:\$userToUnfollowId}) DELETE f")
    fun unfollowUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToUnfollowId") userToUnfollowId: Long,
    )

    @Query("RETURN EXISTS((:User{userId:\$currentUserId})<-[:BLOCKED]-(:User{userId:\$userBlockedId}))")
    fun isUserBlocked(
        @Param("currentUserId") currentUserId: Long,
        @Param("userBlockedId") userBlockedId: Long
    ): Boolean

    @Query("MATCH (u1:User{userId:\$currentUserId}) MATCH (u2:User{userId:\$userToBlockId}) OPTIONAL MATCH (u1)<-[r]-(u2) DELETE r MERGE (u1)-[b:BLOCKED]->(u2) SET b.since = datetime()")
    fun blockUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToBlockId") userToBlockId: Long
    )

    @Query("MATCH (u1:User{userId:\$currentUserId})-[b:BLOCKED]->(u2:User{userId:\$userToUnblockId}) DELETE b")
    fun unblockUser(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToUnblockId") userToUnblockId: Long
    )

    @Query("MATCH (u:User{userId:\$currentUserId})-[f:FOLLOWING]->(following) RETURN following ORDER BY following.id DESC SKIP \$skip LIMIT \$limit")
    fun getUserFollowing(
        @Param("currentUserId") currentUserId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("MATCH (u:User{userId:\$currentUserId})<-[f:FOLLOWING]-(following) RETURN following ORDER BY following.id DESC SKIP \$skip LIMIT \$limit")
    fun getUserFollowers(
        @Param("currentUserId") currentUserId: Long,
        pageable: Pageable
    ): Slice<User>

    @Query("RETURN EXISTS((:User{userId:\$currentUserId})-[:FOLLOWING]->(:User{userId:\$userToFollowId}))")
    fun isUserFollowing(
        @Param("currentUserId") currentUserId: Long,
        @Param("userToFollowId") userToFollowId: Long
    ): Boolean

    @Query("MATCH (u:User{userId:\$currentUserId})<-[f:SENT_FOLLOW_REQUEST]-(users) RETURN users ORDER BY users.id DESC SKIP \$skip LIMIT \$limit")
    fun getPendingFollowRequest(
        @Param("currentUserId") currentUserId: Long,
        pageable: Pageable
    ): Slice<User>
}