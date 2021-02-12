package com.example.neo4j.repository

import com.example.neo4j.model.Post
import com.example.neo4j.model.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest
import org.springframework.data.neo4j.core.Neo4jTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.Neo4jContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DataNeo4jTest
class PostRepositoryTest {

    @Autowired
    lateinit var postRepository: PostRepository

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

    @Test
    fun saves_post() {
        val user = User(userId = 42L, username = "jane-doe", fullName = "Jane Doe")
        var post = Post(postId = 42L, mediaType = "application/json", caption = "some caption", user = user)

        post = postRepository.save(post)

        val persistedPost = template.findById(post.id!!, Post::class.java)
        assertThat(persistedPost).isPresent
            .hasValueSatisfying {
                assertThat(it.id).isNotNull()
                assertThat(it.postId).isEqualTo(post.postId)
                assertThat(it.mediaType).isEqualTo(post.mediaType)
                assertThat(it.caption).isEqualTo(post.caption)
                assertThat(it.user).isNotNull()
                assertThat(it.user.id).isEqualTo(post.user.id)
                assertThat(it.user.userId).isEqualTo(user.userId)
                assertThat(it.user.username).isEqualTo(user.username)
                assertThat(it.user.fullName).isEqualTo(user.fullName)
            }
    }

}
