package br.com.pp.user.controller

import br.com.pp.user.repository.UserRepository
import io.damo.aspen.Test
import io.damo.aspen.spring.SpringTestTreeRunner
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@RunWith(SpringTestTreeRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest : Test() {

    @Autowired
    lateinit var repository: UserRepository

    @LocalServerPort
    val port: Int = 7750

    init {
        before {

        }

        describe("Find all persons active true ") {

            test("# should returns error for empty base") {

            }

            test("# should find all persons indexed") {

            }

            test("# should returns error for empty base") {

            }

            test("# should find all persons indexed") {

            }

            test("# should returns error for empty base") {

            }

            test("# should find all persons indexed") {

            }

        }

        after {

        }
    }
}