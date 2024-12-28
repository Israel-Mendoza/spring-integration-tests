package dev.artisra.integration.repositories

import dev.artisra.integration.entities.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : CrudRepository<Person, Long> {
    fun findByName(name: String): Person?
}