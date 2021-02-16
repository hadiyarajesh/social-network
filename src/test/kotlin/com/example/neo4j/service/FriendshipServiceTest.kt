package com.example.neo4j.service

import com.example.neo4j.model.User
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.neo4j.core.Neo4jTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.Neo4jContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class FriendshipServiceTest {

    @Autowired
    lateinit var friendshipService: FriendshipService

    @Autowired
    lateinit var template: Neo4jTemplate

    companion object {

        @Container
        private val neo4jContainer = Neo4jContainer<Nothing>("neo4j:4.2")

        @DynamicPropertySource
        @JvmStatic
        fun neo4jProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl)
            registry.add("spring.neo4j.authentication.username") { "neo4j" }
            registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword)
        }
    }

    val user3 = User(userId = 3L, username = "user-3", fullName = "User Three")
    final val user2 = User(userId = 2L, username = "user-2", fullName = "User Two")
    val user1 = User(
        userId = 1L, username = "user-1", fullName = "User One"
    )

    @BeforeEach
    fun prepare() {
        template.save(user1)
        template.save(user3)
    }
}