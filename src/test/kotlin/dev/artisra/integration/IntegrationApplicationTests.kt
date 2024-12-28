package dev.artisra.integration

import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationApplicationTests(@Autowired private val mockMvc: MockMvc) {

    @Test
    fun controllerReturns401ForNonAuthenticatedUsers() {
        val request = MockMvcRequestBuilders.get("/people/all")
        mockMvc.perform(request)
            .andExpectAll(
                MockMvcResultMatchers.status().isUnauthorized
            )
    }

    @Test
    @WithMockUser
    fun controllerReturnsWholeListOfPeople() {
        val request = MockMvcRequestBuilders.get("/people/all")
        mockMvc.perform(request)
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.jsonPath("$", hasSize<Int>(3))
            )
    }

    @Test
    @WithMockUser
    fun controllerReturnsCorrectPersonQueriedById() {
        val request = MockMvcRequestBuilders.get("/people/id/3")
        mockMvc.perform(request)
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.jsonPath("$.name").value("Roberto Silva")
            )
    }

    @Test
    @WithMockUser
    fun controllerReturns404ForNonExistingPerson() {
        val request = MockMvcRequestBuilders.get("/people/id/3000")
        mockMvc.perform(request)
            .andExpectAll(
                MockMvcResultMatchers.status().isNotFound,
            )
    }
}
