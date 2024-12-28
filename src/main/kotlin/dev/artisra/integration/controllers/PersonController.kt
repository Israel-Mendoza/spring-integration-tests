package dev.artisra.integration.controllers

import dev.artisra.integration.entities.Person
import dev.artisra.integration.models.PersonIn
import dev.artisra.integration.services.PersonService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/people")
class PersonController(@Autowired private val personService: PersonService) {
    @GetMapping("/all")
    fun getAllPeople(@AuthenticationPrincipal user: UserDetails): ResponseEntity<List<Person>> {
        logger.info("/all requested by ${user.username}")
        return ResponseEntity.ok(personService.getAllPeople())
    }

    @GetMapping("/id/{id}")
    fun getPersonById(@PathVariable id: Long): ResponseEntity<Person> {
        val person = personService.getPersonById(id)
        person?.let { return ResponseEntity.ok(it) }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/name/{name}")
    fun getPersonByName(@PathVariable name: String): ResponseEntity<Person> {
        val person = personService.getPersonByName(name)
        person?.let { return ResponseEntity.ok(it) }
        return ResponseEntity.notFound().build()
    }

    @PostMapping("/new")
    fun createPerson(@RequestBody personRequest: PersonIn): ResponseEntity<Long> {
        val newId = personService.createPerson(personRequest)
        if (newId != -1L) {
            return ResponseEntity(newId, HttpStatus.CREATED)
        }
        return ResponseEntity.badRequest().build()
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(PersonController::class.java)
    }
}