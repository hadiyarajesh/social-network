package com.example.neo4j

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Neo4jApplication

fun main(args: Array<String>) {
    runApplication<Neo4jApplication>(*args)
}
