package br.com.pp.user.controller

import br.com.pp.user.domain.User
import br.com.pp.user.repository.UserRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDateTime
import java.util.UUID

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    lateinit var repository: UserRepository

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    @Order(1)
    @DisplayName("should returns error 404 when search not found")
    fun shouldReturnsError404WhenSearchNotFound() {
        var name = "Rubens Monfasano"
        var userName = "runbens.monfa"

        repository.insert(User(UUID.randomUUID().toString(), "Olavo Castro", "olavo.castro", 1, LocalDateTime.now()))
                .subscribe()

        webTestClient.get()
                .uri("/api/users/name/$name/username/$userName")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .exchange()
                .expectStatus()
                .isNotFound
                .expectBody()
                .jsonPath("$.message").isEqualTo("User not found with parameters informed")
    }

    @Test
    @Order(2)
    @DisplayName("should return error 412 when page is equals or greater zero")
    fun shouldReturnError412WhenPageIsEqualsOrGreaterZero() {
        var name = "Rubens Monfasano"
        var userName = "runbens.monfa"

        repository.insert(User(UUID.randomUUID().toString(), name, userName, 1, LocalDateTime.now()))
                .subscribe()

        webTestClient.get()
                .uri("/api/users/name/$name/username/$userName?page=0&size=15")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody()
                .jsonPath("$.message").isEqualTo("The page or size must be greater than zero")
    }

    @Test
    @Order(3)
    @DisplayName("should return error 412 when size is equals or greater zero")
    fun shouldReturnError412WhenSizeIsEqualsOrGreaterZero() {
        var name = "Rubens Monfasano"
        var userName = "runbens.monfa"

        repository.insert(User(UUID.randomUUID().toString(), name, userName, 1, LocalDateTime.now()))
                .subscribe()

        webTestClient.get()
                .uri("/api/users/name/$name/username/$userName?page=1&size=0")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
                .expectBody()
                .jsonPath("$.message").isEqualTo("The page or size must be greater than zero")
    }

    @Test
    @Order(4)
    @DisplayName("should return user order by rank one, two, three and name")
    fun shouldReturnUserOrderByRankOneTwoThreeAndName() {
        var name = "And"
        var userName = "and"

        repository.insert(User(UUID.randomUUID().toString(), "Anderson Silva", "anderson.silva", 2, LocalDateTime.now())).subscribe()
        repository.insert(User(UUID.randomUUID().toString(), "Aline Santana", "aline.santana", 1, LocalDateTime.now())).subscribe()
        repository.insert(User(UUID.randomUUID().toString(), "Andre Santos", "andre.santos", 1, LocalDateTime.now())).subscribe()
        repository.insert(User(UUID.randomUUID().toString(), "Andre Tadeu", "andre.tadeu", 3, LocalDateTime.now())).subscribe()

        webTestClient.get()
                .uri("/api/users/name/$name/username/$userName")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .exchange()
                .expectStatus()
                .isOk
                .expectBody()
                .jsonPath("$.[0].name").isEqualTo("Andre Santos")
                .jsonPath("$.[0].userName").isEqualTo("andre.santos")
                .jsonPath("$.[1].name").isEqualTo("Anderson Silva")
                .jsonPath("$.[1].userName").isEqualTo("anderson.silva")
                .jsonPath("$.[2].name").isEqualTo("Andre Tadeu")
                .jsonPath("$.[2].userName").isEqualTo("andre.tadeu")
    }

    @Test
    @Order(5)
    @DisplayName("should return user order by rank two, three and name")
    fun shouldReturnUserOrderByRankTwoThreeAndName() {
        var name = "And"
        var userName = "and"

        repository.insert(User(UUID.randomUUID().toString(), "Anderson Silva", "anderson.silva", 2, LocalDateTime.now())).subscribe()
        repository.insert(User(UUID.randomUUID().toString(), "Andre Santos", "andre.santos", 3, LocalDateTime.now())).subscribe()

        repository.findAll().count().block()

        webTestClient.get()
                .uri("/api/users/name/$name/username/$userName")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .exchange()
                .expectStatus()
                .isOk
                .expectBody()
                .jsonPath("$.[0].name").isEqualTo("Anderson Silva")
                .jsonPath("$.[0].userName").isEqualTo("anderson.silva")
                .jsonPath("$.[1].name").isEqualTo("Andre Santos")
                .jsonPath("$.[1].userName").isEqualTo("andre.santos")
    }

    @AfterEach
    fun tearDown() {
        repository.deleteAll().subscribe()
    }
}