package br.com.pp.user.controller

import br.com.pp.user.domain.User
import br.com.pp.user.dto.PageDto
import br.com.pp.user.dto.UserDto
import br.com.pp.user.exception.NotFoundException
import br.com.pp.user.exception.PaginatorException
import br.com.pp.user.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/users", produces = [APPLICATION_JSON_UTF8_VALUE])
@CrossOrigin(value = "http://localhost", allowedHeaders = ["Access-Control-Allow-Origin"], maxAge = 900)
class UserController(val userService: UserService) {

    companion object {
        const val ZERO_VALUE = 0

        val logger: Logger = LoggerFactory.getLogger(UserController::class.java)
    }

    @GetMapping("/name/{name}/username/{userName}")
    fun findByNameAndUserName(@PathVariable name: String,
                              @PathVariable userName: String,
                              @RequestParam(defaultValue = "1") pageCurrent: Long,
                              @RequestParam(defaultValue = "15") pageSize: Long): Mono<PageDto<List<UserDto>>> {
        logger.info("Search user informed name {} and user name {}", name, userName)

        if (ZERO_VALUE >= pageCurrent || ZERO_VALUE >= pageSize) {
            logger.error("Error in paging information. Page {} and Size {}", pageCurrent, pageSize)
            var pEx = PaginatorException()
            logger.error("Occurrence: ", pEx)
            throw pEx
        }

        var users = userService.findByNameAndUserName(name, userName)
                .switchIfEmpty(Mono.error(NotFoundException()))

        return getPagination(users, pageCurrent, pageSize)

    }

    private fun getPagination(users: Flux<User>, pageCurrent: Long, pageSize: Long): Mono<PageDto<List<UserDto>>> {
        var total = users.count().block() as Long

        var elements = users.skip((pageCurrent - 1) * pageSize)
                .take(pageSize)
                .map { UserDto(it.id, it.name, it.userName) }
                .collectList()
                .block() as List<UserDto>

         return Mono.just(PageDto(elements,pageCurrent, pageSize, total))

    }
}