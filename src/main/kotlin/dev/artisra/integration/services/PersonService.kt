package dev.artisra.integration.services

import dev.artisra.integration.entities.Person
import dev.artisra.integration.models.PersonIn
import dev.artisra.integration.repositories.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class PersonService(@Autowired private val personRepository: PersonRepository) {
    fun getAllPeople(): List<Person> = personRepository.findAll().toList()

    fun getPersonById(id: Long): Person? = personRepository.findById(id).getOrNull()

    fun getPersonByName(name: String): Person? = personRepository.findByName(name)

    fun createPerson(personRequest: PersonIn): Long {
        var newPerson = Person(name = personRequest.name, age = personRequest.age)
        try {
            newPerson = personRepository.save(newPerson)
        } catch (ex: Exception) {
            logger.error("Error when saving $newPerson")
            return -1
        }
        return newPerson.id
    }

    companion object {
        val logger = LoggerFactory.getLogger(PersonService::class.java)
    }
}