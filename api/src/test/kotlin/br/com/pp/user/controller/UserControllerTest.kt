package br.com.pp.user.controller

import br.com.pp.user.domain.User
import br.com.pp.user.repository.UserRepository
import io.damo.aspen.Test
import io.damo.aspen.spring.SpringTestTreeRunner
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.util.*

@ActiveProfiles("test")
@RunWith(SpringTestTreeRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest : Test() {

    @Autowired
    lateinit var repository: UserRepository

    @Autowired
    lateinit var webTestClient: WebTestClient

    init {

        describe("Find user informed name and user name") {

            test("# should returns error 404 for search not found") {
                var name = "Rubens Monfasano"
                var userName = "runbens.monfa"

                repository.save(User(UUID.randomUUID().toString(), "Olavo Castro", "olavo.castro", 1, LocalDateTime.now()))

                webTestClient.get()
                        .uri("/api/users/name/$name/username/$userName")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                        .expectBody()
                        .jsonPath("$.message").isEqualTo("User not found with parameters informed")
            }

            test("# should return error 412 for page is equals or greater 0") {
                var name = "Rubens Monfasano"
                var userName = "runbens.monfa"

                repository.save(User(UUID.randomUUID().toString(), name, userName, 1, LocalDateTime.now()))

                webTestClient.get()
                        .uri("/api/users/name/$name/username/$userName?page=0&size=15")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .exchange()
                        .expectStatus()
                        .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                        .expectBody()
                        .jsonPath("$.message").isEqualTo("The page or size must be greater than zero")

            }

            test("# should return error 412 for size is equals or greater 0") {
                var name = "Rubens Monfasano"
                var userName = "runbens.monfa"

                repository.save(User(UUID.randomUUID().toString(), name, userName, 1, LocalDateTime.now()))

                webTestClient.get()
                        .uri("/api/users/name/$name/username/$userName?page=1&size=0")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .exchange()
                        .expectStatus()
                        .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                        .expectBody()
                        .jsonPath("$.message").isEqualTo("The page or size must be greater than zero")
            }

            test("# should find all persons indexed") {

            }

            test("# should returns error for empty base") {

            }

            test("# should find all persons indexed") {

            }

        }

        after {
            repository.deleteAll()
        }
    }
}